package com.imcapp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

public class HistorialFrame extends JFrame {
    // Componentes de la interfaz
    private JTable table;
    private JButton btnBorrarSeleccionados;
    private JButton btnBorrarTodos;
    private List<MedicionIMC> historialActualizado;

    // Constructor
    public HistorialFrame(List<MedicionIMC> historial) {
        setTitle("Historial de Mediciones");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana en tamaño completo

        // Crear la tabla
        String[] columnNames = { "Nombre", "Edad", "Peso (kg)", "Altura (en metros)", "IMC", "Estado" }; // Nombres de
                                                                                                         // las columnas
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Crear el modelo de la tabla
        table = new JTable(model); // Crear la tabla

        // Cambiar la fuente de los campos de texto
        Font font = new Font("Arial", Font.PLAIN, 20); // Crear una nueva fuente
        table.setFont(font); // Establecer la nueva fuente para la tabla

        // Cambiar el tamaño de los títulos de las columnas
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        // Cambiar el ancho de las columnas
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200); // Cambiar el ancho preferido de la columna
        }

        // Centrar el texto en las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Agregar las filas
        JScrollPane scrollPane = new JScrollPane(table); // Crear el scroll pane
        add(scrollPane); // Agregar el scroll pane a la ventana

        // Organizar la interfaz con BorderLayout
        setLayout((new BorderLayout()));
        add(scrollPane, BorderLayout.CENTER); // Agregar el scroll pane a la ventana (al panel

        // Panel para los botones en la parte superior derecha
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnBorrarSeleccionados = new StyledButton("Borrar Seleccionados", new Color(77, 77, 77));
        btnBorrarSeleccionados.addActionListener(e -> {
            borrarRegistrosSeleccionados();
        });
        buttonsPanel.add(btnBorrarSeleccionados); // Agregar el botón a la ventana (al panel)

        btnBorrarTodos = new StyledButton("Borrar Todos", new Color(204, 0, 0));
        btnBorrarTodos.addActionListener(e -> {
            borrarTodosRegistros();
        });
        buttonsPanel.add(btnBorrarTodos); // Agregar el botón a la ventana (al panel)

        add(buttonsPanel, BorderLayout.NORTH); // Agregar el panel de botones a la ventana (al panel)

        // Guardar copia del historial original
        historialActualizado = new ArrayList<>(historial);

        cargarHistorial(historial); // Cargar el historial
    }

    // Método para cargar el historial
    private void cargarHistorial(List<MedicionIMC> historial) {
        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) table.getModel(); // Obtener el modelo de la tabla

        // Agregar las filas
        for (MedicionIMC medicion : historial) {

            // Redondear IMC a un decimal

            Object[] rowData = { // Crear un arreglo con los datos de la fila

                    medicion.getNombre(),
                    medicion.getEdad(),
                    medicion.getPeso(),
                    medicion.getAltura(),
                    medicion.getIMCRedondeado(),
                    medicion.getEstadoIMC()
            };
            model.addRow(rowData); // Agregar la fila al modelo
        }
    }

    private void borrarTodosRegistros() {
        DefaultTableModel model = (DefaultTableModel) table.getModel(); // Obtener el modelo de la tabla
        model.setRowCount(0); // Borrar todas las filas
        historialActualizado.clear();
    }

    private void borrarRegistrosSeleccionados() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] selectedRows = table.getSelectedRows(); // Obtener las filas seleccionadas

        if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                model.removeRow(selectedRows[i]); // Borrar la fila

                MedicionIMC medicion = historialActualizado.get(selectedRows[i]); // Obtener la medición

                Calculadora.borrarMedicion(medicion);

                historialActualizado.remove(selectedRows[i]); // Actualizar el historial
            }
        }
    }

    // Mantener este código para posible futura implementación
    // private List<MedicionIMC> getHistorialActualizado() {
    // return new ArrayList<>(historialActualizado);
    // }
}