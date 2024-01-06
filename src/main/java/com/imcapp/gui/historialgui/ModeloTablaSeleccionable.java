package com.imcapp.gui.historialgui;

import javax.swing.table.DefaultTableModel;

public class ModeloTablaSeleccionable extends DefaultTableModel {
    private static final long serialVersionUID = 1L; // Número de versión para la serialización
    private Class<?>[] tiposClases = new Class<?>[] { Boolean.class, String.class, Integer.class, Double.class, Double.class, String.class, String.class };

            

    private boolean[] editable = new boolean[] { true, false, false, false, false, false };

    public ModeloTablaSeleccionable(Object[][] dato, Object[] nombresColumnas) {
        super(dato, nombresColumnas);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return tiposClases[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable[columnIndex];
    }
}