
package Controls;

import java.util.ArrayList;
import java.util.List;

import Models.Param;

public class FormatAndFillParams {

    public static void main(String[] args) {

        String params = "  SqlValue[1] = 1 ";

        String sql = "SELECT * FROM MEMBER_ASSIGN WHERE PROJECT_NO = ? AND PROJECT_KEIKAKU_NO = ? AND MEMBER_ASSIGN_KBN = ? AND MEMBER_ASSIGN_KOTEI_NO = ? AND MEMBER_ASSIGN_GENKAMEISAI_NO = ? AND MEMBER_SAGYO_KBN = ? AND MEMBER_ASSIGN_KYOKI_NO = ? AND MEMBER_ASSIGN_GAIKO_MEMBER_NO = ? ORDER BY MEMBER_ASSIGN_NO ASC";

        new FormatAndFillParams().extractResult(sql, params);
    }

    public String extractResult(String sql, String params) {

        if (sql.trim().length() == 0 || params.trim().length() == 0) {
            return "Please input arguments...";
        }

        int type = this.getTypeOfParams(params);

        List<String> listParams = null;
        if (type == 2) {
            listParams = this.extractParamsFromViewTypeTwo(params);
        }

        else if (type == 1) {
            listParams = this.extractParamsFromViewTypeOne(params);
        }

        else {
//            listParams = this.extractParamsFromViewTypeThree(params);
        }

        sql = this.formatSql(sql);
        sql = this.fillParams(sql, listParams);

        return sql;
    }

    public int getTypeOfParams(String params) {
        int type = 1;

        if (params.contains("binding") && params.contains("parameter")) {

            return 3;
        }

        for (int i = 0; i < params.length(); i++) {
            if (params.charAt(i) == '\n') {
                return 2;
            }
        }

        if (params.toLowerCase().contains("SqlValue[".toLowerCase())
                || params.toLowerCase().contains("[1]".toLowerCase())
                || params.toLowerCase().contains("qlValue[".toLowerCase())) {
            if (params.contains("=")) {
                return 2;
            }
        }

        return type;
    }

    public List<String> extractParamsFromViewTypeOne(String str) {
        List<String> list = new ArrayList<String>();

        str = str.trim();

        if (str.charAt(0) == '[') {
            str = str.substring(1, str.length());
        }

        if (str.charAt(str.length() - 1) == ']') {
            str = str.substring(0, str.length() - 1);
        }

        int i, j;

        for (i = 0; i < str.length(); i++) {

            j = i + 1;
            for (; j < str.length(); j++) {
                if (str.charAt(j) == ',') {
                    list.add(str.substring(i, j).trim());
                    i = j;
                    break;
                }
            }

            if (j == str.length()) {
                list.add(str.substring(i, j).trim());
                break;
            }

        }

        return list;
    }

    public List<String> extractParamsFromViewTypeTwo(String str) {
        List<String> list = new ArrayList<String>();

        str = str.trim();

        int i, j;

        for (i = 0; i < str.length(); i++) {

            if (str.charAt(i) == '=') {
                for (j = i + 1; j < str.length(); j++) {
                    if (str.charAt(j) == '\n') {
                        list.add(str.substring(i + 1, j).trim());
                        i = j;
                        break;
                    }
                }

                if (j == str.length()) {
                    list.add(str.substring(i + 1, j).trim());
                }
            }
        }

        return list;
    }

    public List<Param> extractParamsFromViewTypeThree(String strParam) {
        List<Param> list = new ArrayList<Param>();

        strParam = strParam.trim();
        int i, j;
        Param param = null;

        String[] strArrays = strParam.split(System.getProperty("line.separator"));

        for (String str : strArrays) {

            param = new Param();

            for (i = str.length() - 1; i >= 0; i--) {

                if (str.charAt(i) == ']') {

                    for (j = i - 1;; j--) {

                        if (str.charAt(j) == '[') {

                            param.setValueParam(str.substring(i, j));
                        }
                    }
                }
            }

            list.add(param);
        }

        return list;
    }

    public String formatSql(String sql) {

        // TODO : format sql
        return sql;
    }

    public String fillParams(String sql, List<String> listParams) {

        int i = 0, j;

        String kq = "";

        int dem = 0;

        for (i = 0; i < sql.length(); i++) {
            if (sql.charAt(i) == '?') {
                dem++;
            }
        }

        if (dem != listParams.size()) {
            return "Not enough parameters!!!";
        }

        dem = 0;

        for (i = 0; i < sql.length(); i++) {
            for (j = i + 1; j < sql.length(); j++) {
                if (sql.charAt(j) == '?') {
                    kq += sql.substring(i, j) + "'";
                    kq += listParams.get(dem++) + "' ";
                    i = j;
                    break;
                }

            }

            if (j == sql.length()) {
                kq += sql.substring(i, j);
                break;
            }
        }

        return kq;
    }
}
