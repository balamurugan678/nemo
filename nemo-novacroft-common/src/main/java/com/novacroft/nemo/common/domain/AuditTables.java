package com.novacroft.nemo.common.domain;

import com.novacroft.nemo.common.utils.StringUtil;

import javax.persistence.*;

/**
 * Table to store the tables that need to be audited.
 *
 * @author patrick.pacheco
 */
@Entity
public class AuditTables {

    protected long id;
    protected String tableName;
    protected String showTableName;

    @SequenceGenerator(name = "AUDITTABLES_SEQ", sequenceName = "AUDITTABLES_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUDITTABLES_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "TABLENAME")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        setShowTableName(tableName);
    }

    @Transient
    public String getShowTableName() {
        return showTableName;
    }

    public void setShowTableName(String showTableName) {
        this.showTableName = StringUtil.initCap(showTableName);
    }

}
