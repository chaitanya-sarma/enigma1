/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.icrisat.genomicSelection;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.icrisat.genomicSelection.connectToDB.GenotypeDB;
import org.icrisat.genomicSelection.connectToDB.PhenotypeDB;

/**
 *
 * @author Mohan Creating GUI dialog box which genotype,phenotype and result
 *         directory are selected Open dialog should be called first when stared
 *         project to mainly get result directory and at least one genotype file
 *         and phenotype file to start analysis
 */
public class OpenDial extends JDialog implements ActionListener {

	public int validate_geno = 0, validate_pheno = 0;
	String lastFileChoosen = "";
	ClosableTabbedPane tabbedPane;
	JLabel lblGeno, lblPheno, lblResultDir;
	JTextField txtGenotype, txtPhenotype, txtResultDir;
	JButton btnGenotype, btnGenoDB, btnPhenotype, btnPhenoDB, btnResultDir, btnOk, btnCancle;
	Frame frame;

	// stare region
	/*
	 * adding the action listener for buttons
	 */
	public OpenDial(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.frame = parent;
		setSize(new Dimension(600, 250));
		tabbedPane = new ClosableTabbedPane();

		setTitle("Load files");
		setLocationRelativeTo(frame);
		setLayout(new GridBagLayout());
		createComponents();
		addComponents();
		setResizable(false);
		String resultDirPath = Constant.resultdirectory; // getting the result
															// directory path
		if (!"empty".equals(resultDirPath)) {
			txtResultDir.setText(resultDirPath);// set the path allready choosen
												// by the user
			txtResultDir.setEditable(false);
			txtResultDir.setOpaque(true); // setting the resultdir text field
											// opaque
			txtResultDir.setMaximumSize(new Dimension(180, 0));
			txtResultDir.setFont(new java.awt.Font("DejaVu Sans", 0, 15));
			btnResultDir.setOpaque(true);// setting result button opaque
			btnResultDir.setEnabled(false); // setting the btnresult button
											// enabled flase
		} else { // checking result directory is empty or not
			btnResultDir.addActionListener(this); // if empty enable action
													// listner for btnresult
		}

		// intilizing action lisener of buttons
		btnGenotype.addActionListener(this);
		btnGenoDB.addActionListener(this);
		btnPhenotype.addActionListener(this);
		btnPhenoDB.addActionListener(this);
		btnOk.addActionListener(this);
		btnCancle.addActionListener(this);
		Constant.genotype = null;
		Constant.phenotype = null;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource(); // gettig the source (name of the
											// button)

		if (source == btnOk) {
			String geno = txtGenotype.getText(); // getting the genotype
													// filename
			String pheno = txtPhenotype.getText(); // getting the phenotype
													// filename
			String result = txtResultDir.getText(); // getting the result
													// directory path
			String geno_filename = new File(geno).getName();
			String pheno_filename = new File(pheno).getName();
			// checking the genotype,phenotype and result directory is empty
			if (txtGenotype.getText().trim().equals("") || txtPhenotype.getText().trim().equals("")
					|| txtResultDir.getText().trim().equals("")) {
				// displaying a messagedialog box
				JOptionPane.showMessageDialog(rootPane, "Please select the * fields \n* fields are mandatory");
			} else {
				if (Constant.genoList.contains(geno_filename) || Constant.phenoList.contains(pheno_filename)) {
					if (Constant.genoList.contains(geno_filename) && Constant.phenoList.contains(pheno_filename)) {
						JOptionPane.showMessageDialog(rootPane,
								"Genotype & Phenotype  file selected already opened \nPlease select other genotype file");
					} else {
						if (Constant.genoList.contains(geno_filename)) {
							JOptionPane.showMessageDialog(rootPane,
									"Genotype file selected already opened \nPlease select other genotype file");
						}
						if (Constant.phenoList.contains(pheno_filename)) {
							JOptionPane.showMessageDialog(rootPane,
									"Phenotype file selected already opened \nPlease select other genotype file");
						}
					}
				} else {
					Constant.resultdirectory = result; // setting the result
														// directory as constant
														// for this session
					validate_geno = 1;
					validate_pheno = 1;
					// checking whether geno and pheno files dn't have any
					// errors
					if (validate_geno == 1 && validate_pheno == 1) {
						Constant.genotype = geno;
						Constant.phenotype = pheno;
						dispose();
					} else {
						setVisible(false);
						GsMethods gg = new GsMethods();
						// if geno file have errors then it is 0
						if (validate_geno == 0) {
							// reading the geno error file and displaying it
							JScrollPane textFileReader = gg
									.textFileReader(Constant.resultdirectory + geno_filename + "_errors.txt");
							tabbedPane.add(textFileReader);
						}
						if (validate_pheno == 0) {
							// reading the error file and display it
							JScrollPane textFileReader = gg
									.textFileReader(Constant.resultdirectory + pheno_filename + "_errors.txt");
							tabbedPane.add(textFileReader);
						}
					}
				}
			}

		}
		if (source == btnCancle) { // if cancle button is selected
			Constant.genotype = null;
			Constant.phenotype = null;
			dispose(); // close the dialog box and also unallocated the memory
						// also assigned to it
		}

		if (source == btnGenotype) { // if source is genotype button
			JFileChooser chooser;
			// getting the perviously choosen directory
			lastFileChoosen = Constant.browsepath;
			// checking the last file choosen is empty or not
			if (lastFileChoosen.isEmpty()) {
				chooser = new JFileChooser();
			} else {
				chooser = new JFileChooser(lastFileChoosen);
			}
			chooser.removeChoosableFileFilter(chooser.getFileFilter());
			chooser.setDialogTitle("Select a genotype file");// setting the
																// dialog title
			chooser.setApproveButtonText("Select File");
			// setting the file filter
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma delimited) (*.csv)", "csv");
			chooser.setFileFilter(filter);
			int r = chooser.showOpenDialog(rootPane);

			if (r == JFileChooser.APPROVE_OPTION) {
				txtGenotype.setText(chooser.getSelectedFile().getPath());
				File fileValidation = new File(txtGenotype.getText());
				boolean genotypeExists = fileValidation.exists();
				Constant.browsepath = chooser.getSelectedFile().getAbsolutePath();
				if (genotypeExists == false) {
					JOptionPane.showMessageDialog(rootPane,
							"Selected file doesnot exists \nPlease select valid genotype file");
					txtGenotype.setText("");
				}
			}

			if (r == JFileChooser.CANCEL_OPTION) {
				// donothing
			}
		}

		if (source == btnPhenoDB) {
			PhenotypeDB phenoDB = new PhenotypeDB(frame, true);
        	phenoDB.setVisible(true);
		}

		if(source == btnGenoDB){
			GenotypeDB genoDB = new GenotypeDB(frame, true);
        	genoDB.setVisible(true);
		}
		if (source == btnPhenotype) { // if the phenotype button
			JFileChooser chooser;
			// getting the browsePath if file already choosen
			lastFileChoosen = Constant.browsepath;
			// check whether it is empty or not
			if (lastFileChoosen.isEmpty()) {
				chooser = new JFileChooser();
			} else {
				chooser = new JFileChooser(lastFileChoosen);
			}
			chooser.removeChoosableFileFilter(chooser.getFileFilter());
			chooser.setDialogTitle("Select a phenotype  file");// setting the
																// title
			chooser.setApproveButtonText("Select File");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV (Comma delimited) (*.csv)", "csv"); // adding
																													// the
																													// file
																													// filter
																													// csv
			chooser.setFileFilter(filter);
			int r = chooser.showOpenDialog(rootPane);

			if (r == JFileChooser.APPROVE_OPTION) {
				txtPhenotype.setText(chooser.getSelectedFile().getPath());
				Constant.browsepath = chooser.getSelectedFile().getAbsolutePath();
				File fileValidation = new File(txtPhenotype.getText());
				boolean phenotypeExists = fileValidation.exists();
				Constant.browsepath = chooser.getSelectedFile().getAbsolutePath();
				if (phenotypeExists == false) {
					JOptionPane.showMessageDialog(rootPane,
							"Selected file doesnot exists \nPlease select valid phenotype file");
					txtPhenotype.setText("");
				}
			}

			if (r == JFileChooser.CANCEL_OPTION) {
				// donothing
			}
		}

		if (source == btnResultDir) { // if the result button selected
			JFileChooser folderchooser = new JFileChooser(); // initilizing the
																// jfilechooser
			folderchooser.setDialogTitle("Select a folder where result should be stored"); // setting
																							// the
																							// title
			folderchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // setting
																				// the
																				// condiation
																				// to
																				// select
																				// only
																				// directorys
			folderchooser.removeChoosableFileFilter(folderchooser.getFileFilter());
			// disable the "All files" option.
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Any Folder", "FOLDER");
			folderchooser.setFileFilter(filter);
			folderchooser.setApproveButtonText("Select Folder");
			folderchooser.setApproveButtonToolTipText("Select the folder for working and result directoy");
			if (folderchooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				OSCheck.OSType os = OSCheck.getOperatingSystemType();
				if ("Window".equals(os.toString())) {
					// Getting the selected directory path and setting to
					// resultdir text field
					txtResultDir.setText(folderchooser.getSelectedFile().toString() + "\\");
					if (Files.isDirectory(Paths.get(txtResultDir.getText()))) {
						// folder is exists
						// donothing
					} else {
						// folder not exits
						int option = JOptionPane.showOptionDialog(rootPane,
								"Folder path doesnot exits \nDo you want to create folder for the path",
								"New Folder Creation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
								null, null);
						if (option == JOptionPane.YES_OPTION) {
							File fileCreate = new File(txtResultDir.getText());
							boolean mkdir = fileCreate.mkdir();
						}
						if (option == JOptionPane.NO_OPTION) {
							txtResultDir.setText("");
						}
					}
				}
				if ("Linux".equals(os.toString())) {
					// Getting the selected directory path and setting to
					// resultdir text field

					txtResultDir.setText(folderchooser.getSelectedFile().toString() + "/");
					if (Files.isDirectory(Paths.get(txtResultDir.getText()))) {
						// folder is exists
					} else {
						// folder not exits
						int option = JOptionPane.showOptionDialog(rootPane,
								"Folder path doesnot exits \nDo you want to create folder for the path",
								"New Folder Creation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
								null, null);
						if (option == JOptionPane.YES_OPTION) {
							File fileCreate = new File(txtResultDir.getText());
							boolean mkdir = fileCreate.mkdir();
						}
						if (option == JOptionPane.NO_OPTION) {
							txtResultDir.setText("");
						}
					}
				}
				if ("MacOS".equals(os.toString())) {
					// Getting the selected directory path and setting to
					// resultdir text field
					txtResultDir.setText(folderchooser.getSelectedFile().toString() + "/");
				}
			}
		}
	}

	private void addComponents() {
		GridBagConstraints gc = new GridBagConstraints();

		//First Row
		gc.gridy = 0;

		gc.gridx = 0;
		gc.insets = new Insets(0, 0, 10, 10);
		gc.anchor = GridBagConstraints.LINE_START;
		add(lblGeno, gc);

		gc.gridx = 1;
		add(txtGenotype, gc);

		gc.gridx = 2;
		add(btnGenotype, gc);

		gc.gridx = 3;
		add(btnGenoDB, gc);

		// Second Row
		gc.gridy = 1;
		
		gc.gridx = 0;
		add(lblPheno, gc);

		gc.gridx = 1;
		add(txtPhenotype, gc);

		gc.gridx = 2;
		add(btnPhenotype, gc);

		gc.gridx = 3;
		add(btnPhenoDB, gc);

		// Third line
		gc.gridy = 2;
		
		gc.gridx = 0;
		add(lblResultDir, gc);

		gc.gridx = 1;
		// gc.anchor = GridBagConstraints.NONE;
		add(txtResultDir, gc);

		//gc.weightx = 5;
		gc.gridx = 2;
		gc.gridwidth = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		// gc.anchor = GridBagConstraints.NONE;
		add(btnResultDir, gc);

		// 4th line
		gc.gridx = 2;
		gc.gridy = 3;
		gc.gridwidth = 1;
		gc.insets = new Insets(20, 0, 10, 10);
		gc.anchor = GridBagConstraints.LINE_END;
		add(btnOk, gc);

		gc.gridx = 3;
		gc.anchor = GridBagConstraints.LINE_START;
		add(btnCancle, gc);
	}

	private void createComponents() {
		// -----------------------------Geno----------------------------------------
		lblGeno = new JLabel("Genotype file");
		lblGeno.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		txtGenotype = new JTextField(20);
		txtGenotype.setEditable(false);
		txtGenotype.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		btnGenotype = new JButton("Browse");
		btnGenotype.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		btnGenoDB = new JButton("Connect");
		btnGenoDB.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N
		//btnGenoDB.setIcon(Util.createIcon("Download_database_Icon.gif"));
		
		// ---------------------------------Pheno------------------------------------
		lblPheno = new JLabel("Phenotype file");
		lblPheno.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		txtPhenotype = new JTextField(20);
		txtPhenotype.setEditable(false);
		txtPhenotype.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		btnPhenotype = new JButton("Browse");
		btnPhenotype.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		btnPhenoDB = new JButton("Connect");
		btnPhenoDB.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N
		//btnPhenoDB.setIcon(Util.createIcon("Download_database_Icon.gif"));
		
		// --------------------------------Location-----------------------------------
		lblResultDir = new JLabel("Result Directory");
		lblResultDir.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		txtResultDir = new JTextField(20);
		txtResultDir.setEditable(false);
		txtResultDir.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		btnResultDir = new JButton("Browse");
		btnResultDir.setFont(new Font("DejaVu Sans", 0, 15)); // NOI18N

		btnOk = new JButton("   OK   ");
		btnOk.setFont(new Font("DejaVu Sans", Font.BOLD, 15)); // NOI18N

		btnCancle = new JButton("Cancel");
		btnCancle.setFont(new Font("DejaVu Sans", Font.BOLD, 15)); // NOI18N

	}
}
