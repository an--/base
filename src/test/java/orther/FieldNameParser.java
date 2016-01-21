package orther;

import sun.management.counter.Variability;

public class FieldNameParser {
    
    public static String columnToField(String columnStr) {
        
        String[] columns = columnStr.split(",");
        StringBuilder fieldsBuilder = new StringBuilder();
        for (int i = 0, l = columns.length; i < l; i++) {
            int index = columns[i].indexOf(".");
            String columnName = columns[i];
            if (0 <= index) {
                columnName = columns[i].substring(index + 1);
            }
            fieldsBuilder.append(columns[i]).append(" ").append(replace_ToCamel(columnName)).append(",\n");
        }
        return fieldsBuilder.toString();
    }
    
    public static String replace_ToCamel(String str){
        if (null == str) {
            return null;
        }
        
        String _Str = str.trim();
        int strLength = _Str.length();
        if (0 == strLength) {
            return "";
        }
        
        int index = _Str.indexOf('_');
        if (0 > index) {
            return _Str;
        } if (0 == index) {
            _Str = _Str.substring(0, _Str.length());
        } else {
            String str1 = _Str.substring(0, index);
            String temp = str1;
            if (index < strLength - 1) {
                String str2 = String.valueOf(_Str.charAt(index + 1));
                temp += str2.toUpperCase();
            }
            if (index < strLength - 2) {
                String str3 = _Str.substring(index + 2);
                temp += str3;
            }
            _Str = temp;
        }
        return replace_ToCamel(_Str); 
    }
    
    public static void main(String[] main) {
        String str = "loan.loan_type_id,loan.loan_channel_id,loan.name,loan.min_terms,loan.max_terms,"
                + "loan.allow_terms,loan.min_amount,loan.max_amount,loan.repayment_id,loan.min_duration,"
                + "loan.commission_rate,loan.commission,loan.service_fee_rate,loan.service_fee_by,"
                + "loan.interest,loan.interest_by,loan.description,loan.condition,loan.materials,"
                + "loan.tips,loan.fee_desc,loan.interface,loan.apply_url,loan.params,"
                + "loan.apply_count,loan.success_count,loan.sequence,loan.notify_email,"
                + "loan.online,loan.application_status_url,loan.enable_for_xdb,loan.cps,loan.cpa,"
                + "loan.cps_price,loan.success_rate,loan.price_rate,"
                + "loan.created_time,loan.duplicate_to_detail,loan.duplicate_apply_abort,loan.jiedianqian_show";
        System.out.println(columnToField(str));
    } 

}


