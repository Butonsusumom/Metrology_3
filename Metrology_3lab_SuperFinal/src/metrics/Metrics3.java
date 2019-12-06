package metrics;

import fileWork.FileWorker;
import parsing.RemoveCom;
import parsing.Search;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// P   не модифф не упр
// М модиф пееменные не управл константы и переменные
// С управл переменные
// T мусор ( не выводятся в вычислениях не учавствуют )

public class Metrics3 {

    private Search search ;
    public ArrayList<String> P, M , C ,T, P1, M1 , C1 ,T1  ;
    public  LinkedHashMap<String, Integer> operands_Array, operandsMnePlevat ;
    int newTextBegin  ;
    // мне очень лень писать getter и setter а это лаба никому не сделась

    public Metrics3 ( String filePath) {
        search = new Search(FileWorker.readFle(filePath));

        search.setCode(RemoveCom.removeComments(search.getCode()) );
        search.setCode( search.getCode().replaceAll("\".+\"", " ") );
    }

    public LinkedHashMap<String, Integer> searchOperands () {

        operands_Array = new LinkedHashMap<>();

        operands_Array = search.search_opeands(  //
                "(String|int|float|double|byte|short|char|boolean)\\s+?[A-Za-z].*?(;|\\)[^[\\\n|\\s]]*)", operands_Array) ;

     //   operands_Array = search.search_opeandsOOP("[A-Z]\\w*?\\s+?[A-Za-z].*?(;|\\))", operands_Array) ;
        operandsMnePlevat = operands_Array ;
         for (Map.Entry operand : operands_Array.entrySet()) {
           search.setCode(search.getCode().replaceAll("--"+operand.getKey(),operand.getKey()+" = lol "));
           search.setCode(search.getCode().replaceAll("\\+\\+"+operand.getKey(),operand.getKey()+" = lol "));
        }
         search.setCode(search.getCode().replaceAll("\\("," ( "));
        search.setCode(search.getCode().replaceAll("\\)"," ) "));

        defineMetric (operands_Array);
        operands_Array = removeOperands(operands_Array);
        defineMetric1(operands_Array);



        return operands_Array;
    }

    private void defineMetric (LinkedHashMap<String, Integer> operands_Array) {

        P = new ArrayList<>();
        M = new ArrayList<>();
        C = new ArrayList<>();
        T = new ArrayList<>();

        String regex;
        Pattern Search_pattern;
        Matcher Search_matcher;

        for (Map.Entry operand : operands_Array.entrySet()) {

            regex = "(for|if|while|switch)\\W\\s*?\\(.*?\\W" + operand.getKey() + "\\W+.*?\\)";
            if ( searchMetric(operand, regex) ) C.add((String) operand.getKey());
            else {

                regex = "case\\s+?" + operand.getKey() + "\\s*:";
                if ( searchMetric(operand, regex) ) C.add((String) operand.getKey());
                else {

                    regex = "\\t*?\\s*?" + operand.getKey() + "\\s*?=\\s*?.+?(next|write)\\w*?\\s*?\\(.*?\\)\\s*?;";
                    if (!searchMetric(operand, regex)) {

                        regex = "=\\s*?.*?\\s+?" + operand.getKey(); // kosak s probelom

                        if (!searchMetric(operand, regex)) {

                            regex = "(print|println|write).*?\\s*?.*?\\W" + operand.getKey() + ".*?\\)";

                            if (!searchMetric(operand, regex)) {
                                T.add((String) operand.getKey());
                            } else M.add((String) operand.getKey());

                        } else {
                            M.add((String) operand.getKey());
                        }

                    } else {

                        regex = "(;|\\{|\\))\\t*?\\s*?" + operand.getKey() + "\\s*?(\\*|\\+|-|/){0,1}=\\s*.+?;";

                        Search_pattern = Pattern.compile(regex);
                        Search_matcher = Search_pattern.matcher(search.getCode().substring(newTextBegin));

                        if (Search_matcher.find()) {
                            System.out.println(search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
                            M.add((String) operand.getKey());
                        } else {

                            regex = "(;|\\{|\\))\\t*?\\s*?[^[;{)]]+?\\s*?=\\s*?[^[;{)]]*?\\s*?" + operand.getKey() + "\\s*?.*?;";

                            Search_pattern = Pattern.compile(regex);
                            Search_matcher = Search_pattern.matcher(search.getCode());

                            if (Search_matcher.find()) {
                                System.out.println("!" + search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
                                P.add((String) operand.getKey());
                            } else {
                                regex = "(print|println|write).*?\\s*?\\(.*?\\s+?" + operand.getKey() + "\\s+?.*?\\)";
                                Search_pattern = Pattern.compile(regex);
                                Search_matcher = Search_pattern.matcher(search.getCode());

                                if (!Search_matcher.find()) T.add((String) operand.getKey());
                                P.add((String) operand.getKey());

                            }
                        }
                    }
                }
            }
        }
    }





    public int defineAmountCol () {
        if ( M.size() >= P.size() && M.size() >= C.size() && M.size() >= T.size()) return M.size() ;
        if ( P.size() >= M.size() && P.size() >= C.size() && P.size() >= T.size()) return P.size() ;
        if ( C.size() >= M.size() && C.size() >= P.size() && C.size() >= T.size()) return C.size();
       else return T.size() ;

    }

    private LinkedHashMap<String, Integer>  removeOperands (LinkedHashMap<String, Integer> operands_Array) {
        String regex;
        Pattern Search_pattern;
        Matcher Search_matcher;
        LinkedHashMap<String, Integer> newOperands_Array = new LinkedHashMap<>();

        for (Map.Entry operand : operands_Array.entrySet()) {

            regex = "(print|read).*?\\s*?\\(.*?\\W" + operand.getKey() + "\\W";

            Search_pattern = Pattern.compile(regex);
            Search_matcher = Search_pattern.matcher(search.getCode());

            if (Search_matcher.find()) {
                System.out.println(search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
                newOperands_Array.put((String) operand.getKey(), (int) operand.getValue());
            } else {

                regex = operand.getKey() + "\\s*?=\\s*?.+?(next|read).*?\\(";

                Search_pattern = Pattern.compile(regex);
                Search_matcher = Search_pattern.matcher(search.getCode());

                if (Search_matcher.find()) {
                    System.out.println(search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
                    newOperands_Array.put((String) operand.getKey(), (int) operand.getValue());
                }
            }
        }
        return newOperands_Array;
    }


    private void defineMetric1 (LinkedHashMap<String, Integer> operands_Array) {

        P1 = new ArrayList<>();
        M1 = new ArrayList<>();
        C1 = new ArrayList<>();
        T1 = new ArrayList<>();

        String regex;
        Pattern Search_pattern;
        Matcher Search_matcher;

        for (Map.Entry operand : operands_Array.entrySet()) {

            regex = "(for|if|while|switch)\\W\\s*?\\(.*?\\W" + operand.getKey() + "\\W+.*?\\)";
            if ( searchMetric(operand, regex) ) C1.add((String) operand.getKey());
            else {

                regex = "case\\s+?" + operand.getKey() + "\\s*:";
                if ( searchMetric(operand, regex) ) C1.add((String) operand.getKey());
                else {

                    regex = "\\t*?\\s*?" + operand.getKey() + "\\s*?=\\s*?.+?(next|write)\\w*?\\s*?\\(.*?\\)\\s*?;";
                    if (!searchMetric(operand, regex)) {

                        regex = "=\\s*?.*?\\s+?" + operand.getKey(); // kosak s probelom

                        if (!searchMetric(operand, regex)) {

                            regex = "(print|println|write).*?\\s*?.*?\\W" + operand.getKey() + ".*?\\)";

                            if (!searchMetric(operand, regex)) {
                                T1.add((String) operand.getKey());
                            } else M1.add((String) operand.getKey());

                        } else {
                            M1.add((String) operand.getKey());
                        }

                    } else {

                        regex = "(;|\\{|\\))\\t*?\\s*?" + operand.getKey() + "\\s*?(\\*|\\+|-|/){0,1}=\\s*.+?;";

                        Search_pattern = Pattern.compile(regex);
                        Search_matcher = Search_pattern.matcher(search.getCode().substring(newTextBegin));

                        if (Search_matcher.find()) {
                            System.out.println(search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
                            M1.add((String) operand.getKey());
                        } else {

                            regex = "(;|\\{|\\))\\t*?\\s*?[^[;{)]]+?\\s*?=\\s*?[^[;{)]]*?\\s*?" + operand.getKey() + "\\s*?.*?;";

                            Search_pattern = Pattern.compile(regex);
                            Search_matcher = Search_pattern.matcher(search.getCode());

                            if (Search_matcher.find()) {
                                System.out.println("!" + search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
                                P1.add((String) operand.getKey());
                            } else {
                                regex = "(print|println|write).*?\\s*?\\(.*?\\s+?" + operand.getKey() + "\\s+?.*?\\)";
                                Search_pattern = Pattern.compile(regex);
                                Search_matcher = Search_pattern.matcher(search.getCode());

                                if (!Search_matcher.find()) T1.add((String) operand.getKey());
                                P1.add((String) operand.getKey());

                            }
                        }
                    }
                }
            }
        }
    }

    boolean searchMetric (Map.Entry operand, String regex) {

        Pattern Search_pattern = Pattern.compile(regex);
        Matcher Search_matcher = Search_pattern.matcher(search.getCode());

        if (Search_matcher.find()) {
            System.out.println(search.getCode().substring(Search_matcher.start(), Search_matcher.end()));
            newTextBegin = Search_matcher.end() ;
            return true;

        } else {
            newTextBegin = 0 ;
            return false ;
        }

    }


}
