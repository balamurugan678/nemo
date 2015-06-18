DECLARE
-- drop all objects in a schema - use with care!
  l_sql VARCHAR2(1024);
  CURSOR c_objects IS
    SELECT
      o.object_name,
      o.object_type
    FROM user_objects o
    WHERE o.object_type IN ('FUNCTION', 'PACKAGE', 'PROCEDURE', 'SEQUENCE', 'SYNONYM', 'TABLE', 'TYPE', 'VIEW');
BEGIN
  FOR r_object IN c_objects LOOP
    l_sql := 'drop ' || r_object.object_type || ' "' || r_object.object_name || '"';
    IF r_object.object_type = 'TABLE'
    THEN
      l_sql := l_sql || ' cascade constraints';
    END IF;
    IF r_object.object_type = 'TYPE'
    THEN
      l_sql := l_sql || ' force';
    END IF;
    EXECUTE IMMEDIATE l_sql;
  END LOOP;
  EXCEPTION
  WHEN OTHERS THEN
  raise_application_error(-20000, 'Error: ' || sqlcode || ' - ' || sqlerrm || ' on: ' || l_sql);
END;
/
