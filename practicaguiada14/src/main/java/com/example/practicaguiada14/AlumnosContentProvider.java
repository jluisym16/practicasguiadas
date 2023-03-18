package com.example.practicaguiada14;

import static android.graphics.Insets.add;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;
public class AlumnosContentProvider extends ContentProvider {
    private static int id = 1;
    private final Map<Integer, Alumno> alumnos = new HashMap<>();
    public static final String AUTHORITY = "fp.dam.pmdm.guiadaxiv.provider";

    //tabla virtual donde se guardan los datos de los alumnos
    public static final Uri TABLA_ALUMNOS_URI = Uri.parse("content://" + AUTHORITY + "/alumnos");

    //nombres de las diferentes columnas de la tabla
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_APELLIDOS = "apellidos";
    public static final String COL_CURSO = "curso";

    //valida URI's del proveedor (TABLA_ALUMNOS_URI)
    private static final UriMatcher uriMatcher;
    private static final int TABLA_ALUMNOS = 1;
    private static final int FILA_ALUMNOS = 2;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "alumnos", TABLA_ALUMNOS);
        uriMatcher.addURI(AUTHORITY, "alumnos/#", FILA_ALUMNOS);
    }

    //inicializar tabla con algunos datos
    @Override
    public boolean onCreate() {
        alumnos.put(id++, new Alumno("Juan", "Valle", "1º ESO"));
        alumnos.put(id++, new Alumno("Miguel", "Navas", "2º ESO"));
        alumnos.put(id++, new Alumno("Irene", "Cuenca", "1º ESO"));
        alumnos.put(id++, new Alumno("Alicia", "Pérez", "3º ESO"));
        alumnos.put(id++, new Alumno("Luis", "Conde", "4º ESO"));
        alumnos.put(id++, new Alumno("Fernanda", "Sevilla", "3º ESO"));
        return true;
    }

    //se crea un cursor con el que recorrer la tabla
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        int match = uriMatcher.match(uri);
        if (match == FILA_ALUMNOS)
            add(cursor, Integer.parseInt(uri.getLastPathSegment()));
        else if (match == TABLA_ALUMNOS)
            alumnos.keySet().forEach(key -> add(cursor, key));
        return cursor;
    }
    private void add(MatrixCursor cursor, int id) {
        Alumno alumno;
        synchronized (alumnos) {
            alumno = alumnos.get(id);
        }
        if (alumno != null)
            cursor.addRow(new Object[]{id, alumno.getNombre(), alumno.getApellidos(),
                    alumno.getCurso()});
    }

    //crea un objeto alumno y lo añade a la estructura de datos
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) == TABLA_ALUMNOS) {
            synchronized (alumnos) {
                alumnos.put(id, new Alumno(values.getAsString(COL_NOMBRE),
                        values.getAsString(COL_APELLIDOS), values.getAsString(COL_CURSO)));
            }
            return ContentUris.withAppendedId(uri, id++);
        }
        return null;
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    //recibe uri de fila, si existe, extrae el id y borra los datos
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int size = alumnos.size();
        if (uriMatcher.match(uri) == FILA_ALUMNOS)
            synchronized (alumnos) {
                alumnos.remove(Integer.parseInt(uri.getLastPathSegment()));
            }
        return size - alumnos.size();
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TABLA_ALUMNOS:
                return "vnd.android.cursor.dir/vnd.mentor.alumno";
            case FILA_ALUMNOS:
                return "vnd.android.cursor.item/vnd.mentor.alumno";
            default:
                return null;
        }
    }
}