package com.sousou.sever;

public class Test02 {
    public static void main(String[] args){

        DataBase db= new DataBase("jdbc:mysql://localhost:3306/sousou","kinlon","gdadmin");

        db.contect();
        db.statement();

        db.runSQL("" +
                "SELECT\n" +
                "cobom_set.ID,\n" +
                "cobom_set.RENT\n" +
                "FROM\n" +
                "cobom_set\n" +
                "WHERE\n" +
                "cobom_set.ID = 1"
        );
        String[][] a=db.getResult();
        System.out.println(a[0][1]);

        db.closeDataBase();




    }
}
