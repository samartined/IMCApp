package com.imcapp.gui.historialgui;

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

import com.imcapp.gui.StyledButton;
import com.imcapp.logica.Calculadora;
import com.imcapp.logica.MedicionIMC;

public class HistorialFrame extends JFrame {
    // Componentes de la interfaz
    private JTable tabla;
    private JButton btnBorrarSeleccionados;
    private JButton btnBorrarTodos;
    private List<MedicionIMC> historialActualizado;
    private ModeloTablaSeleccionable modeloTabla;

    // Constructor
    public HistorialFrame(List<MedicionIMC> historial) {
        setTitle("Historial de Mediciones");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre la ventana en tamaño completo

        // Crear la tabla
        String[] nombresColumnas = { "Seleccionar", "Nombre", "Edad", "Peso (kg)", "Altura (en metros)", "IMC",
                "Estado" };
        modeloTabla = new ModeloTablaSeleccionable(new Object[][] {}, nombresColumnas);
        tabla = new JTable(modeloTabla); // Crear la tabla

        // Cambiar la fuente de los campos de texto
        Font fuente = new Font("Arial", Font.PLAIN, 20); // Crear una nueva fuente
        tabla.setFont(fuente); // Establecer la nueva fuente para la tabla

        // Cambiar el tamaño de los títulos de las columnas
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        // Cambiar el ancho de las columnas
        TableColumn columna;
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            columna = tabla.getColumnModel().getColumn(i);
            columna.setPreferredWidth(200); // Cambiar el ancho preferido de la columna
        }

        // Centrar el texto en las celdas
        DefaultTableCellRenderer centroRender = new DefaultTableCellRenderer();
        centroRender.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        tabla.setDefaultRenderer(Object.class, centroRender);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centroRender);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centroRender);
        tabla.getColumnModel().getColumn(3).setCellRenderer(centroRender);
        tabla.getColumnModel().getColumn(4).setCellRenderer(centroRender);

        // Agregar las filas
        JScrollPane scrollPanel = new JScrollPane(tabla); // Crear el scroll pane
        add(scrollPanel); // Agregar el scroll pane a la ventana

        // Organizar la interfaz con BorderLayout
        setLayout((new BorderLayout()));
        add(scrollPanel, BorderLayout.CENTER); // Agregar el scroll pane a la ventana (al panel

        // Panel para los botones en la parte superior derecha
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        panelBotones.setBorder(new EmptyBorder(10, 10, 10, 10));

        btnBorrarSeleccionados = new StyledButton("Borrar Seleccionados", new Color(77, 77, 77));
        btnBorrarSeleccionados.addActionListener(e -> {
            borrarRegistrosSeleccionados();
        });
        panelBotones.add(btnBorrarSeleccionados); // Agregar el botón a la ventana (al panel)

        btnBorrarTodos = new StyledButton("Borrar Todos", new Color(204, 0, 0));
        btnBorrarTodos.addActionListener(e -> {
            borrarTodosRegistros();
        });
        panelBotones.add(btnBorrarTodos); // Agregar el botón a la ventana (al panel)

        add(panelBotones, BorderLayout.NORTH); // Agregar el panel de botones a la ventana (al panel)

        // Guardar copia del historial original
        historialActualizado = new ArrayList<>(historial);

        cargarHistorial(historial); // Cargar el historial
    }

    // Método para cargar el historial
    private void cargarHistorial(List<MedicionIMC> historial) {
        // Borrar las filas para evitar duplicados
        modeloTabla.setRowCount(0);

        // Agregar las filas
        for (MedicionIMC medicion : historial) {

            Object[] datosFila = { // Crear un arregwlo con los datos de la fila
                    false, // Checkbox
                    medicion.getNombre(),
                    medicion.getEdad(),
                    medicion.getPeso(),
                    medicion.getAltura(),
                    medicion.getIMCRedondeado(),
                    medicion.getEstadoIMC()
            };
            modeloTabla.addRow(datosFila); // Agregar la fila al modelo
        }

        // Cambiar el alto de las filas
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.setRowHeight(i, 70);
        }

        // Cambiar el espaciado entre las celdas
        tabla.setIntercellSpacing(new java.awt.Dimension(2, 0));
    }

    private void borrarTodosRegistros() {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel(); // Obtener el modelo de la tabla
        modelo.setRowCount(0); // Borrar todas las filas
        historialActualizado.clear();
    }

    private void borrarRegistrosSeleccionados() {
        int conteoFilas = modeloTabla.getRowCount(); // Obtener el número de filas

        for (int i = conteoFilas - 1; i >= 0; i--) {
            Boolean seleccionado = (Boolean) modeloTabla.getValueAt(i, 0);

            if (seleccionado) {
                modeloTabla.removeRow(i);
                MedicionIMC medicion = historialActualizado.get(i);
                Calculadora.borrarMedicion(medicion);
                historialActualizado.remove(i);
            }
        }
    }

    // Mantener este código para posible futura implementación
    // private List<MedicionIMC> getHistorialActualizado() {
    // return new ArrayList<>(historialActualizado);
    // }
}