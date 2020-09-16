package objs;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringBuilderHelp {
    public static void replaceAll(StringBuilder sb, String find, String replace){

        //compile pattern from find string
        Pattern p = Pattern.compile(find);

        //create new Matcher from StringBuilder object
        Matcher matcher = p.matcher(sb);

        //index of StringBuilder from where search should begin
        int startIndex = 0;

        while( matcher.find(startIndex) ){

            sb.replace(matcher.start(), matcher.end(), replace);

            //set next start index as start of the last match + length of replacement
            startIndex = matcher.start() + replace.length();
        }
    }
}
