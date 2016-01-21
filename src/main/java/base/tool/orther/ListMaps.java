package base.tool.orther;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListMaps {
    
    /**
     * 查找 List<Map<String, Object>> 中所有的指定字段的值，根据 paramName,paramValue 过滤
     * 
     * @param sourceListMap
     * @param fieldName
     * @param classT,
     * @param paramName
     * @param paramValue
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> listMapFindFieldAllValue(List<Map<String, Object>> sourceListMap,
                                                   String fieldName ,
                                                   Class<T> classT,
                                                   String paramName, 
                                                   Object paramValue) {
        if (null == paramName || null == sourceListMap ) {
            return null;
        }

        if (sourceListMap.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<T> resultList = new ArrayList<>();
            for (Map<String, Object> sourceMap : sourceListMap) {
                if (sourceMap.containsKey(paramName)) {
                    Object sourceValue = sourceMap.get(paramName);
                    if ((null == paramValue && null == sourceValue)
                            || paramValue != null && paramValue.equals(sourceValue)) {
                        resultList.add((T)sourceMap.get(fieldName));
                    }
                }
            }
            return resultList;
        }
    }

    /**
     * 过滤 {@code List<Map<String, Object>>}
     *
     * @param sourceListMap
     * @return
     */
    public List<Map<String, Object>> listMapFilter(List<Map<String, Object>> sourceListMap,
                                                   String paramName, Object paramValue) {
        if (null == paramName || null == sourceListMap ) {
            return null;
        }

        if (sourceListMap.isEmpty()) {
            return sourceListMap;
        } else {
            List<Map<String, Object>> resultList = new ArrayList<>();
            for (Map<String, Object> sourceMap : sourceListMap) {
                if (sourceMap.containsKey(paramName)) {
                    Object sourceValue = sourceMap.get(paramName);
                    if ((null == paramValue && null == sourceValue)
                            || paramValue != null && paramValue.equals(sourceValue)) {
                        resultList.add(sourceMap);
                    }
                }
            }
            return resultList;
        }
    }

    /**
     * 过滤 {@code List<Map<String, Object>>}
     *
     * @param sourceListMap
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> listMapFilter(List<Map<String, Object>> sourceListMap,
                                          Map<String, Object> paramMap) {
        if (null == paramMap || null == sourceListMap ) {
            return null;
        }

        if (paramMap.isEmpty() || sourceListMap.isEmpty()) {
            return sourceListMap;
        } else {
            List<Map<String, Object>> resultList = new ArrayList<>();
            Set<Map.Entry<String, Object>> paramEntrySet = paramMap.entrySet();
            for (Map<String, Object> sourceMap : sourceListMap) {
                boolean isContain = true;
                Iterator<Map.Entry<String, Object>> paramEntryIt = paramEntrySet.iterator();
                while (paramEntryIt.hasNext()) {
                    Map.Entry<String,Object> paramEntry = paramEntryIt.next();
                    String paramKey = paramEntry.getKey();
                    Object paramValue = paramEntry.getValue();
                    if (!sourceMap.containsKey(paramKey)) {
                        isContain = false;
                        break;
                    }
                    Object sourceValue = sourceMap.get(paramKey);

                    if ((null == paramValue && null != sourceValue)
                            || !paramValue.equals(sourceValue)) {
                        isContain = false;
                        break;
                    }
                }

                if (isContain) {
                    resultList.add(sourceMap);
                }
            }
            return resultList;
        }
    }

}
