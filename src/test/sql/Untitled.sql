SELECT 
    attr.id,
    attr.name,
    attr.attribute_type attributeType,
    attrv.id attributeValueId,
    attrv.value,
    base.baseId
FROM
    attr
        LEFT JOIN
    attribute_value attrv ON attr.id = attrv.attr_id
        LEFT JOIN
    (SELECT 
        MIN(id) baseId, attr_id
    FROM
        attribute_value
    GROUP BY attr_id) base ON attr.id = base.attr_id
    
    -- where attr.id = 2
