package first;

import java.util.Scanner;

public class App {
    private App() {
    }

    public static void main(String[] args)
            throws java.io.IOException, org.xml.sax.SAXException, de.l3s.boilerpipe.BoilerpipeProcessingException {

        int selection = 1;
        Scanner input = new Scanner(System.in);

        while (selection != 0) {
            String searchTerm;
            String URL;
            try {
                System.out.println(UserInterface.mainUi());
                selection = input.nextInt();
                input.nextLine();

                switch (selection) {
                case 1:
                    System.out.println(UserInterface.newCompanyPrompt());
                    searchTerm = input.nextLine();
                    System.out.println(UserInterface.urlPrompt());
                    URL = input.nextLine();

                    PortfolioNewsAnalyzer analyzer = new PortfolioNewsAnalyzer();
                    analyzer.addPortfolioCompany(searchTerm);
                    if (analyzer.analyzeArticle(URL)) {
                        System.out.println("your company is mentioned");
                    } else {
                        System.out.println("Your company is not mentioned");
                    }
                    break;
                case 2:
                    System.out.println(UserInterface.urlPrompt());
                    URL = input.nextLine();
                    String temp = detectLang.getLangFromText(URL);
                    System.out.println(temp);
                    break;
                case 0:
                    break;
                default:
                    System.out.println(UserInterface.errorMessage());
                    break;
                }
            } catch (Exception e) {
                System.out.println(UserInterface.errorMessage());
                selection = 1;
                input.nextLine();
            }
        }

        input.close();
        System.out.println(UserInterface.exitMessage());
    }
}
