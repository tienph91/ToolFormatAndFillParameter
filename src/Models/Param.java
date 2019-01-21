package Models;

public class Param implements Comparable<Param> {

    private String valueParam;
    private String indexParam;
    private String typeParam;

    public String getValueParam() {
        return valueParam;
    }

    public void setValueParam(String valueParam) {
        this.valueParam = valueParam;
    }

    public String getIndexParam() {
        return indexParam;
    }

    public void setIndexParam(String indexParam) {
        this.indexParam = indexParam;
    }

    public String getTypeParam() {
        return typeParam;
    }

    public void setTypeParam(String typeParam) {
        this.typeParam = typeParam;
    }

    public Param(String valueParam, String indexParam, String typeParam) {
        super();
        this.valueParam = valueParam;
        this.indexParam = indexParam;
        this.typeParam = typeParam;
    }

    public Param() {
        super();
    }

    @Override
    public int compareTo(Param o) {
        int index1 = Integer.valueOf(this.getIndexParam());
        int index2 = Integer.valueOf(o.getIndexParam());

        if (index1 > index2) {

            return 1;
        }

        if (index1 < index2) {
            return -1;
        }

        return 0;

    }

}
