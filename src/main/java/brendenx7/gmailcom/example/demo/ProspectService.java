package brendenx7.gmailcom.example.demo;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ProspectService {

    public static File compareAndUpdateProspects(File targetListFile, File datasourceFile) throws IOException {
        List<Prospect> oldList = ProspectManager.readProspectsFromExcel(datasourceFile);
        List<Prospect> newList = ProspectManager.readProspectsFromExcel(targetListFile);

        for (Prospect newProspect : newList) {
            boolean matchFound = false;

            for (Prospect oldProspect : oldList) {
                if (newProspect.getEmail().equalsIgnoreCase(oldProspect.getEmail())) {
                    matchFound = true;

                    if (!newProspect.getName().equals(oldProspect.getName())) {
                        oldProspect.setName(newProspect.getName());
                    }
                    if (newProspect.isDecisionMaker() != oldProspect.isDecisionMaker()) {
                        oldProspect.setDecisionMaker(newProspect.isDecisionMaker());
                    }
                    break;
                }
            }

            if (!matchFound) {
                oldList.add(newProspect);
            }
        }

        File outputFile = new File("updated_prospects.xlsx");
        ProspectManager.writeProspectsToExcel(oldList, outputFile);

        return outputFile;
    }
}
