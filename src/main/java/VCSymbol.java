public class VCSymbol extends java_cup.runtime.Symbol {
    private int line;
    private int column;
    private String characters; // Sequence that forms token

    public VCSymbol(int type, int line, int column, String characters) {
        this(type, line, column, -1, -1, null, characters);
    }

    public VCSymbol(int type, int line, int column, Object value, String characters) {
        this(type, line, column, -1, -1, value, characters);
    }

    public VCSymbol(int type, int line, int column, int left, int right, Object value, String characters) {
        super(type, left, right, value);
        this.line = line;
        this.column = column;
        this.characters = characters;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getCharacters() {
        return characters;
    }

}
