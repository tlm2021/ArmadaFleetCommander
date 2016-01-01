package com.travismosley.android.data.database;

import android.database.sqlite.SQLiteQueryBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderTest {

    private static final String TABLE_NAME = "test_table_name";

    private static final String COL1 = "column_one";
    private static final String COL2 = "column_two";

    private static final String GROUP_BY = "test_group_by";
    private static final String ORDER_BY = "test_order_by";

    private static final String TEST_QUERY_FORMAT = "SELECT %s WHERE %s GROUP BY %s ORDER BY %s";
    private String EXPECTED_QUERY_ALL;

    @Before
    public void populateExpectedQueryResult(){
        EXPECTED_QUERY_ALL = getQuery(new String[] {COL1, COL2}, null, GROUP_BY, ORDER_BY);
    }

    private String getQuery(String[] cols, String where, String groupBy, String orderBy){
        String colString = "";
        for (int i=0; i < cols.length; i++) {
            if (i == 0) {
                colString = cols[i];
            }else{
                colString = colString + ", " + cols[i];
            }
        }

        return String.format(TEST_QUERY_FORMAT, colString, where, groupBy, orderBy);
    }


    private class QueryBuilderForTesting extends QueryBuilder{

        @Override
        protected String getTableName(){
            return TABLE_NAME;
        }

        @Override
        protected List<String> getColumns(){
            return new ArrayList<>(Arrays.asList(COL1, COL2));
        }

        @Override
        protected String getGroupBy(){
            return GROUP_BY;
        }

        @Override
        protected String getOrderBy(){
            return ORDER_BY;
        }

    }

    @Mock
    SQLiteQueryBuilder mQueryBuilder;

    @InjectMocks
    QueryBuilderForTesting testQueryBuilder = new QueryBuilderForTesting();

    @Test
    public void queryBuilder_QueryAll_CorrectColumnsAndTableName(){
        /*
        Mockito.when(reader.document(anyInt())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                Object mock = invocation.getMock();
                return document(fakeIndex((int)(Integer)args[0]));
            }
        });

        String query = mQueryBuilder.buildQuery(
                mColumns,                          // SELECT <List>
                whereClause,                       // WHERE <String>
                getGroupBy(),                      // GROUP BY <String>
                null,                              // HAVING <String>
                getOrderBy(),                      // ORDER BY <String>
                null                               // LIMIT <String>
        );
         */

        // Intercept the call the the SqlQueryBuilder, and just return a string that represents the
        // arguments getting passed to it.
        when(mQueryBuilder.buildQuery(Mockito.any(String[].class), // Column List
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString())).thenAnswer(new Answer() {
            public String answer(InvocationOnMock invocation){
                Object[] args = invocation.getArguments();
                // Build a query-like string from the arguments
                return getQuery((String[]) args[0], (String) args[1], (String) args[2], (String) args[4]);
            }
        });

        Assert.assertEquals(EXPECTED_QUERY_ALL, testQueryBuilder.queryAll());

    }
}
