package first;

public class UserInterface {

    public static String mainUi() {
        String ui = "********************************\n" + "* News Analyis app\n"
                + "* Please elect from the options below:\n" + "* \n" + "* 1. Search for Business \n"
                + "* 2. detect Langauge \n" + "* 0. exit\n" + "********************************\n";
        return ui;
    }

    public static String exitMessage() {
        return "Thank you for using the App!";
    }

    public static String newCompanyPrompt() {
        return "Enter the name of the company you wish to search for: ";
    }

    public static String urlPrompt() {
        return "Enter the URL of the website you wish to search: ";
    }

    public static String errorMessage() {
        return "The program encountered an error. Please press \"enter\" then select from the options above";
    }

    public static String languageSupport() {
        String temp = "your language is not supported, current supported languages are: \n"
                + " - English ; French ; Spanish ; Arabic ; German ; Chinese";
        return temp;
    }

}
