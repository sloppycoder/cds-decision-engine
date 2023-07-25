package net.vino9.demo.decisionengine;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.drools.decisiontable.DecisionTableProviderImpl;
import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringReader;

@Slf4j
@Service
public class DecisionService {
    @Value("${app.decision-table-xls}")
    private String decisionTableXls;

//    private StatelessKieSession ksession;
    private KieSession ksession;

    public InputModel getDecision(InputModel input) {
        ksession.insert(input);
        ksession.fireAllRules();
//        ksession.execute(input);
        return input;
    }

    @PostConstruct
    public void init() {
        String drl = getDrlFromXls();
        Resource resource = ResourceFactory.newClassPathResource(decisionTableXls, getClass());
        this.ksession = getKieSession(resource);
//        this.ksession = createStatelessSession();
    }

    private StatelessKieSession createStatelessSession() {
        var source = "/Users/lee/Projects/git/DecisionEngine/src/main/resources/eligibility_rules.xlsx";
        ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
        String drl = converter.compile(source, "Customer", 1, 1);
        System.out.println(drl);

        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        // Add compiled decision table to KnowledgeBuilder
        kbuilder.add(ResourceFactory.newReaderResource(new StringReader(drl)), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            System.out.println(kbuilder.getErrors().toString());
            throw new RuntimeException("Unable to compile drl.");
        }

        // Get KieBase
        KieBase kbase = kbuilder.newKieBase();

        // Get stateless session
        return kbase.newStatelessKieSession();
    }

    private String getDrlFromXls() {
        DecisionTableConfiguration configuration = KnowledgeBuilderFactory.newDecisionTableConfiguration();
        configuration.setInputType(DecisionTableInputType.XLS);
        Resource dt = ResourceFactory.newClassPathResource(decisionTableXls, getClass());
        DecisionTableProviderImpl decisionTableProvider = new DecisionTableProviderImpl();
        String drl = decisionTableProvider.loadFromResource(dt, null);
        return drl;
    }

    private KieSession getKieSession(Resource dt) {
        KieServices kieServices=KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem().write(dt);
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem).buildAll();
        KieRepository kieRepository = kieServices.getRepository();
        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
        KieContainer kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
        KieSession ksession = kieContainer.newKieSession();
        return ksession;
    }
}
