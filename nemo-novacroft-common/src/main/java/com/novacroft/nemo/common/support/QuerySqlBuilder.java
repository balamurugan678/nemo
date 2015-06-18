package com.novacroft.nemo.common.support;

public class QuerySqlBuilder {
    protected StringBuilder sql = new StringBuilder();
    protected final static String SELECT = "select ";
    protected final static String SEPARATOR = ", ";
    protected final static String FROM = " from ";
    protected final static String WHERE = " where ";
    protected final static String AND = " and ";
    protected final static String OR = " or ";
    protected final static String START_PARENTHESIS = " ( ";
    protected final static String END_PARENTHESIS = " ) ";
    protected final static String PARAMETER_EQUALS = " = :";
    protected final static String PARAMETER_LIKE = " like :";

    public QuerySqlBuilder appendFirstSelect(String clause) {
        appendPrefixedClause(SELECT, clause);
        return this;
    }

    public QuerySqlBuilder appendSelect(String clause) {
        appendCommaPrefixedClause(clause);
        return this;
    }

    public QuerySqlBuilder appendFirstTable(String clause) {
        appendPrefixedClause(FROM, clause);
        return this;
    }

    public QuerySqlBuilder appendTable(String clause) {
        appendCommaPrefixedClause(clause);
        return this;
    }

    public QuerySqlBuilder appendTable(Boolean condition, String clause) {
        if (condition) {
            appendTable(clause);
        }
        return this;
    }

    public QuerySqlBuilder appendFirstWhereJoin(String clause) {
        appendPrefixedClause(WHERE, clause);
        return this;
    }

    public QuerySqlBuilder appendAndJoin(String clause) {
        appendAndPrefixedClause(clause);
        return this;
    }

    public QuerySqlBuilder appendAndJoin(Boolean condition, String clause) {
        if (condition) {
            appendAndJoin(clause);
        }
        return this;
    }

    public QuerySqlBuilder appendOrFilter(String clause, String parameterName, Boolean useLike) {
        appendOrPrefixedClause(clause);
        if (useLike) {
            appendLikeParameter(parameterName);
        } else {
            appendEqualsParameter(parameterName);
        }
        return this;
    }
    
    public QuerySqlBuilder appendOrFilter(Boolean condition, String clause, String parameterName, Boolean useLike) {
        if (condition) {
            appendOrFilter(clause, parameterName, useLike);
        }
        return this;
    } 
    
    public QuerySqlBuilder appendAndFilter(String clause, String parameterName, Boolean useLike) {
        appendAndPrefixedClause(clause);
        if (useLike) {
            appendLikeParameter(parameterName);
        } else {
            appendEqualsParameter(parameterName);
        }
        return this;
    }
    
    public QuerySqlBuilder appendAndFilterWithStartParanthasis(Boolean condition, String clause, String parameterName) {
        if (condition) {
            appendAndFilterWithStartParanthasis(clause, parameterName, Boolean.FALSE);
        }
        return this;
    }
    
    public QuerySqlBuilder appendAndFilterWithStartParanthasis(String clause, String parameterName, Boolean useLike) {
        appendAndWithStartParenthesisPrefixedClause(clause);
        if (useLike) {
            appendLikeParameter(parameterName);
        } else {
            appendEqualsParameter(parameterName);
        }
        return this;
    }

    public QuerySqlBuilder appendAndFilter(Boolean condition, String clause, String parameterName, Boolean useLike) {
        if (condition) {
            appendAndFilter(clause, parameterName, useLike);
        }
        return this;
    }

    public QuerySqlBuilder appendAndFilter(Boolean condition, String clause, String parameterName) {
        if (condition) {
            appendAndFilter(clause, parameterName, Boolean.FALSE);
        }
        return this;
    }
    
    public QuerySqlBuilder appendEndParenthesis(Boolean condition, String clause) {
        if(condition){
            this.sql.append(END_PARENTHESIS);
            this.sql.append(clause);
        }
        return this;
    }

    protected void appendCommaPrefixedClause(String clause) {
        appendPrefixedClause(SEPARATOR, clause);
    }

    protected void appendAndPrefixedClause(String clause) {
        appendPrefixedClause(AND, clause);
    }
    
    protected void appendAndWithStartParenthesisPrefixedClause(String clause) {
        appendPrefixedClause(AND + START_PARENTHESIS, clause);
    }
    
    protected void appendOrPrefixedClause(String clause) {
        appendPrefixedClause(OR, clause);
    }

    protected void appendEqualsParameter(String parameterName) {
        this.sql.append(PARAMETER_EQUALS);
        this.sql.append(parameterName);
    }

    protected void appendLikeParameter(String parameterName) {
        this.sql.append(PARAMETER_LIKE);
        this.sql.append(parameterName);
    }

    protected void appendPrefixedClause(String prefix, String clause) {
        this.sql.append(prefix);
        this.sql.append(clause);
    }

    @Override
    public String toString() {
        return this.sql.toString();
    }
}
