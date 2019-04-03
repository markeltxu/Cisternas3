package com.example.cisternas3;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //db.execSQL("create table Cisternas(nombre text primary key ,pass text,matricula text,conductor text,recogidaFecha text,recogidaKm integer, entregaFecha text, entregaKm integer,limpieza text, reparacion text, situacion text, observaciones text)");
            //db.execSQL("insert into Cisternas values('admin','admin12','prueba12','Markel','07/05/2019','1000','09/05/2019','2122','Limpio','no','Bilbao','Todo correcto')");
            db.execSQL("create table CisternasDatos(nombre text,matricula text,recogidaFecha text,recogidaKm text, entregaFecha text, entregaKm text,limpieza text, reparacion text, situacion text, observaciones text)");

            db.execSQL("create table Datos(nombre text primary key,pass text,matricula text)");
            db.execSQL("insert into Datos values('admin','admin12','prueba122222A')");
            db.execSQL("insert into Datos values('markel','markel','cisterna2')");
            db.execSQL("insert into Datos values('cristian','cristian','cisterna3333')");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //db.execSQL("create table Cisternas(nombre text primary key ,pass text,matricula text,conductor text,recogidaFecha text,recogidaKm integer, entregaFecha text, entregaKm integer,limpieza text, reparacion text, situacion text, observaciones text)");
            //db.execSQL("insert into Cisternas values('admin','admin12','prueba12','Markel','07/05/2019','1000','09/05/2019','2122','Limpio','no','Bilbao','Todo correcto')");
            db.execSQL("create table CisternasDatos(nombre text ,matricula text,recogidaFecha text,recogidaKm text, entregaFecha text, entregaKm text,limpieza text, reparacion text, situacion text, observaciones text)");

            db.execSQL("create table Datos(nombre text primary key ,matricula text,pass text)");
            db.execSQL("insert into Datos values('admin','admin12','prueba122222A')");
            db.execSQL("insert into Datos values('markel','markel','cisterna2')");
            db.execSQL("insert into Datos values('cristian','cristian','cisterna3333')");
        }
}
