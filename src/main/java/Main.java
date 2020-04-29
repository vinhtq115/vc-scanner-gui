import java.io.*;

public class Main {
    public static String Result ;

    /**
     * Get token name
     * @param token: Token constant
     * @return Token name from sym interface or "UNKNOWN TOKEN" if not found.
     */
    private static String getTokenName(int token) {
        try {
            java.lang.reflect.Field[] classFields = sym.class.getFields();
            for (java.lang.reflect.Field classField : classFields) {
                if (classField.getInt(null) == token) {
                    return classField.getName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return "UNKNOWN TOKEN";
    }

    public static String getResult() {
        return Result;
    }

    public static void setResult(String result) {
        Result = result;
    }

    /**
     * Scan file and output its token and identifier/classifier.
     * @param input_file_name: Path to input file
     * @param output_file_name: Path to output file
     */
    public static void scanFile(String input_file_name, String output_file_name) throws FileNotFoundException, IOException, Exception {
        Scanner scanner;
        try {
            // Output file
            FileWriter writer = new FileWriter(output_file_name, false);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // Input file
            FileInputStream stream = new FileInputStream(input_file_name);
            Reader reader = new InputStreamReader(stream, java.nio.charset.StandardCharsets.US_ASCII);
            scanner = new Scanner(reader);
            while (!scanner.yyatEOF()) {

                VCSymbol s = (VCSymbol) scanner.next_token();
                if (s.sym == 0) { // Reached end of file
                    break;
                }
                String tokenName = getTokenName(s.sym);

                // Tabbing
                String tab = new String(new char[2 - (tokenName.length() / 8 - 1)]).replace("\0", "\t");

                // Output
                String output = tokenName + tab + s.getCharacters();
                Result += output + "\n";
                // Print
                System.out.println(output);
                // Write to file
                bufferedWriter.write(output);
                bufferedWriter.newLine();
            }
            System.out.println("\n--- End of file ---");
            bufferedWriter.write("\n--- End of file ---");
            bufferedWriter.close();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: \"" + input_file_name + "\"");
        } catch (IOException e) {
            throw new IOException("IO error scanning file \"" + input_file_name + "\"", e);
        } catch (java.lang.Exception e) {
            throw new Exception("Unexpected exception:", e);
        }
    }
}
