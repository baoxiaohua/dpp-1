SELECT distinct c.*,cg.name group_name
FROM computation c
LEFT JOIN computation_group cg ON c.computation_group_id=cg.id
WHERE  c.deleted=false and (:segment1);