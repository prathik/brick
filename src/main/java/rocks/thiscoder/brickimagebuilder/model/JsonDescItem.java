package rocks.thiscoder.brickimagebuilder.model;

public class JsonDescItem {
    Integer r;
    Integer g;
    Integer b;
    String file;

    public Integer getR() {
        return r;
    }

    public void setR(Integer r) {
        this.r = r;
    }

    public Integer getG() {
        return g;
    }

    public void setG(Integer g) {
        this.g = g;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "JsonDescItem{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", file='" + file + '\'' +
                '}';
    }
}
