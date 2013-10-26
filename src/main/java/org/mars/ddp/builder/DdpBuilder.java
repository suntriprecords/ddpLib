package org.mars.ddp.builder;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DdpBuilder {

  private JFrame frame;
  private JTextField textFieldTitle;
  private JTextField textFieldPerformer;
  private JTextField textFieldUpcEan;
  private JTextField textFieldTotalTime;
  private JTextField textFieldDestination;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          DdpBuilder window = new DdpBuilder();
          window.frame.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public DdpBuilder() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout(5, 5));
    
    //Tracks table
    JScrollPane scrollPane = new JScrollPane();
    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
    DdpTable tracksTable = new DdpTable(new DdpTableModel());
    scrollPane.setViewportView(tracksTable);
    
    JPanel panelRight = new JPanel();
    frame.getContentPane().add(panelRight, BorderLayout.EAST);
    panelRight.setLayout(new BorderLayout(0, 0));
    
    JPanel panelAlbum = new JPanel();
    panelRight.add(panelAlbum, BorderLayout.NORTH);
    
    panelAlbum.setBorder(new EmptyBorder(5, 5, 5, 5));
    GridBagLayout gbl_panel = new GridBagLayout();
    panelAlbum.setLayout(gbl_panel);
    
    JLabel lblTitle = new JLabel("Title");
    GridBagConstraints gbc_lblTitle = new GridBagConstraints();
    gbc_lblTitle.anchor = GridBagConstraints.EAST;
    gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
    gbc_lblTitle.gridx = 0;
    gbc_lblTitle.gridy = 0;
    panelAlbum.add(lblTitle, gbc_lblTitle);
    
    textFieldTitle = new JTextField();
    textFieldTitle.setColumns(15);
    GridBagConstraints gbc_textFieldTitle = new GridBagConstraints();
    gbc_textFieldTitle.anchor = GridBagConstraints.WEST;
    gbc_textFieldTitle.insets = new Insets(0, 0, 5, 0);
    gbc_textFieldTitle.gridx = 1;
    gbc_textFieldTitle.gridy = 0;
    panelAlbum.add(textFieldTitle, gbc_textFieldTitle);
    
    JLabel lblPerformer = new JLabel("Performer");
    GridBagConstraints gbc_lblPerformer = new GridBagConstraints();
    gbc_lblPerformer.anchor = GridBagConstraints.EAST;
    gbc_lblPerformer.insets = new Insets(0, 0, 5, 5);
    gbc_lblPerformer.gridx = 0;
    gbc_lblPerformer.gridy = 1;
    panelAlbum.add(lblPerformer, gbc_lblPerformer);
    
    textFieldPerformer = new JTextField();
    textFieldPerformer.setColumns(15);
    GridBagConstraints gbc_textFieldPerformer = new GridBagConstraints();
    gbc_textFieldPerformer.anchor = GridBagConstraints.WEST;
    gbc_textFieldPerformer.insets = new Insets(0, 0, 5, 0);
    gbc_textFieldPerformer.gridx = 1;
    gbc_textFieldPerformer.gridy = 1;
    panelAlbum.add(textFieldPerformer, gbc_textFieldPerformer);
    
    JLabel lblUpcEan = new JLabel("UPC/EAN");
    GridBagConstraints gbc_lblUpcEan = new GridBagConstraints();
    gbc_lblUpcEan.anchor = GridBagConstraints.EAST;
    gbc_lblUpcEan.insets = new Insets(0, 0, 5, 5);
    gbc_lblUpcEan.gridx = 0;
    gbc_lblUpcEan.gridy = 2;
    panelAlbum.add(lblUpcEan, gbc_lblUpcEan);
    
    textFieldUpcEan = new JTextField();
    textFieldUpcEan.setColumns(15);
    GridBagConstraints gbc_textFieldUpcEan = new GridBagConstraints();
    gbc_textFieldUpcEan.anchor = GridBagConstraints.WEST;
    gbc_textFieldUpcEan.insets = new Insets(0, 0, 5, 0);
    gbc_textFieldUpcEan.gridx = 1;
    gbc_textFieldUpcEan.gridy = 2;
    panelAlbum.add(textFieldUpcEan, gbc_textFieldUpcEan);
    
    JLabel lblTotalTime = new JLabel("Total time");
    GridBagConstraints gbc_lblTotalTime = new GridBagConstraints();
    gbc_lblTotalTime.anchor = GridBagConstraints.EAST;
    gbc_lblTotalTime.insets = new Insets(0, 0, 0, 5);
    gbc_lblTotalTime.gridx = 0;
    gbc_lblTotalTime.gridy = 3;
    panelAlbum.add(lblTotalTime, gbc_lblTotalTime);
    
    textFieldTotalTime = new JTextField();
    textFieldTotalTime.setColumns(8);
    GridBagConstraints gbc_textFieldTotalTime = new GridBagConstraints();
    gbc_textFieldTotalTime.anchor = GridBagConstraints.WEST;
    gbc_textFieldTotalTime.gridx = 1;
    gbc_textFieldTotalTime.gridy = 3;
    panelAlbum.add(textFieldTotalTime, gbc_textFieldTotalTime);
    
    JPanel panelButtons = new JPanel();
    panelRight.add(panelButtons, BorderLayout.SOUTH);
    panelButtons.setLayout(new BorderLayout(0, 0));
    
    JButton btnCreate = new JButton("Create");
    btnCreate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //TODO create the DDP here
      }
    });
    panelButtons.add(btnCreate, BorderLayout.SOUTH);
    
    JPanel panelDestination = new JPanel();
    panelButtons.add(panelDestination, BorderLayout.CENTER);
    
    textFieldDestination = new JTextField();
    panelDestination.add(textFieldDestination);
    textFieldDestination.setColumns(20);
    
    JButton buttonDestinationChoose = new JButton("...");
    buttonDestinationChoose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //TODO choose destination folder.
      }
    });
    panelDestination.add(buttonDestinationChoose);
    
    JLabel lblDestination = new JLabel("Destination");
    panelButtons.add(lblDestination, BorderLayout.NORTH);
    
    JPanel panel = new JPanel();
    panelRight.add(panel, BorderLayout.CENTER);
    
    JButton btnAddTrack = new JButton("Add track");
    panel.add(btnAddTrack);
  }

}
