package hzk.util.hash;

import hzk.util.ProgressEvent;
import hzk.util.ProgressObserver;
import hzk.util.TaskSequence;
import hzk.util.Task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class HashUI {

	protected Shell shlHashUi;
	protected Display display;
	private Combo cmbBrowse;
	private Text textResult;
	private ProgressBar progressBar;
	private Label lblPgbar;
	private Button btnCalculate;
	private Button btnPause;
	private Button btnCancel;
	private Button btnSha1,btnMd5 ;

	private Label lblTime;
	private Task hashTask;
	JFileHashTask sha1task;
	JFileHashTask md5task;
	protected Log log = LogFactory.getLog(this.getClass());
	// private Model model=new Model();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					HashUI window = new HashUI();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlHashUi.open();
		shlHashUi.layout();
		while (!shlHashUi.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlHashUi = new Shell();
		shlHashUi.setSize(450, 300);
		shlHashUi.setText("Hash UI");
		shlHashUi.setLayout(new FormLayout());

		cmbBrowse = new Combo(shlHashUi, SWT.BORDER);
		FormData fd_cmbBrowse = new FormData();
		cmbBrowse.setLayoutData(fd_cmbBrowse);

		Button btnBrowse = new Button(shlHashUi, SWT.NONE);
		fd_cmbBrowse.right = new FormAttachment(btnBrowse, -6);
		fd_cmbBrowse.top = new FormAttachment(btnBrowse, 2, SWT.TOP);
		FormData fd_btnBrowse = new FormData();
		fd_btnBrowse.top = new FormAttachment(0, 7);
		fd_btnBrowse.right = new FormAttachment(100, -10);
		btnBrowse.setLayoutData(fd_btnBrowse);
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				FileDialog dialog = new FileDialog(shlHashUi, SWT.OPEN);
				dialog.setFilterNames(new String[] { "All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.*" });
				String p = dialog.open();
				// model.setPath(p);
				if (p != null)
					cmbBrowse.setText(p);
			}
		});
		btnBrowse.setText("Browse");

		Group grpHashItems = new Group(shlHashUi, SWT.NONE);
		fd_cmbBrowse.left = new FormAttachment(grpHashItems, 0, SWT.LEFT);
		FormData fd_grpHashItems = new FormData();
		fd_grpHashItems.bottom = new FormAttachment(0, 102);
		fd_grpHashItems.top = new FormAttachment(0, 37);
		fd_grpHashItems.right = new FormAttachment(0, 101);
		fd_grpHashItems.left = new FormAttachment(0, 5);
		grpHashItems.setLayoutData(fd_grpHashItems);
		grpHashItems.setText("Hash Items");
		
		SelectionAdapter hashItemsLstner=new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btnCalculate.setEnabled(btnSha1.getSelection() || btnMd5.getSelection());
			}
		};
		btnSha1 = new Button(grpHashItems, SWT.CHECK);
		btnSha1.addSelectionListener(hashItemsLstner);
		btnSha1.setSelection(true);
		btnSha1.setBounds(10, 21, 70, 17);
		btnSha1.setText("SHA1");

		btnMd5 = new Button(grpHashItems, SWT.CHECK);
		btnMd5.addSelectionListener(hashItemsLstner);
		btnMd5.setBounds(10, 45, 70, 17);
		btnMd5.setText("MD5");

		
		
		btnCalculate = new Button(shlHashUi, SWT.NONE);
		FormData fd_btnCalculate = new FormData();
		fd_btnCalculate.bottom = new FormAttachment(0, 71);
		fd_btnCalculate.left = new FormAttachment(100, -152);
		fd_btnCalculate.top = new FormAttachment(0, 38);
		fd_btnCalculate.right = new FormAttachment(100, -10);
		btnCalculate.setLayoutData(fd_btnCalculate);

		btnCalculate.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				if (!btnSha1.getSelection() && !btnMd5.getSelection()){
					return;
				}
				String path = cmbBrowse.getText();
				cmbBrowse.add(path);
				if (path == null) {
					textResult.setText("Error:File Not Found");
				}
				
				textResult.setText("");
				sha1task=md5task=null;
				if (btnSha1.getSelection())
					sha1task=newHashTask(path,"SHA1");
				if (btnMd5.getSelection())
					 md5task=newHashTask(path,"MD5");
				hashTask=new TaskSequence(sha1task,md5task);
				hashTask.start();

			}
		});
		btnCalculate.setText("Calculate");

		textResult = new Text(shlHashUi, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_textResult = new FormData();
		fd_textResult.top = new FormAttachment(grpHashItems, 46);
		fd_textResult.left = new FormAttachment(0, 5);
		fd_textResult.right = new FormAttachment(100, -10);
		textResult.setLayoutData(fd_textResult);
		textResult.setEditable(false);
		// textResult.setVisible(false);
		progressBar = new ProgressBar(shlHashUi, SWT.NONE);
		FormData fd_progressBar = new FormData();
		fd_progressBar.right = new FormAttachment(100, -10);
		fd_progressBar.left = new FormAttachment(0, 5);
		fd_progressBar.bottom = new FormAttachment(100, -10);
		progressBar.setLayoutData(fd_progressBar);
		progressBar.setVisible(false);

		lblPgbar = new Label(shlHashUi, SWT.NONE);
		fd_textResult.bottom = new FormAttachment(lblPgbar, -6);
		FormData fd_lblPgbar = new FormData();
		fd_lblPgbar.right = new FormAttachment(100, -220);
		fd_lblPgbar.bottom = new FormAttachment(100, -33);
		fd_lblPgbar.left = new FormAttachment(0, 5);
		lblPgbar.setLayoutData(fd_lblPgbar);

		btnPause = new Button(shlHashUi, SWT.NONE);
		btnPause.setEnabled(false);
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if ("Pause".equals(btnPause.getText())) {
					btnPause.setText("Resume");
				} else {
					btnPause.setText("Pause");
				}
				hashTask.pauseOrResume();
				
			}
		});
		FormData fd_btnPause = new FormData();
		fd_btnPause.right = new FormAttachment(100, -10);
		fd_btnPause.top = new FormAttachment(0, 71);
		btnPause.setLayoutData(fd_btnPause);
		btnPause.setText("Pause");

		lblTime = new Label(shlHashUi, SWT.NONE);
		FormData fd_lblTime = new FormData();
		fd_lblTime.left = new FormAttachment(100, -106);
		fd_lblTime.top = new FormAttachment(textResult, 6);
		fd_lblTime.right = new FormAttachment(100, -10);
		lblTime.setLayoutData(fd_lblTime);
		btnPause.setText("Pause");
		lblTime.setLayoutData(fd_lblTime);
		
		btnCancel = new Button(shlHashUi, SWT.NONE);
		fd_btnPause.left = new FormAttachment(btnCancel, 6);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				progressBar.setVisible(false);
				hashTask.cancel();			

			}
		});
		btnCancel.setEnabled(false);
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.left = new FormAttachment(btnCalculate, 10, SWT.LEFT);
		fd_btnCancel.top = new FormAttachment(0, 71);
		fd_btnCancel.right = new FormAttachment(100, -78);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");

	}
	
	private JFileHashTask  newHashTask(String path,String algorithm){
		final int pbMaximum = progressBar.getMaximum();
		JFileHashTask task=JFileHasher.createTask(path,algorithm);
		task.addObserver(new ProgressObserver() {
			@Override
			public void progressUpdated(final ProgressEvent e) {
				final int sel = (int) (pbMaximum * e.getProgressRate());
				display.asyncExec(new Runnable() {
					public void run() {
						if (progressBar.isDisposed())
							return;
						progressBar.setSelection(sel);
						lblPgbar.setText(e.getStatus());
						lblTime.setText(e.getTaskRunTimeInSec());

					}
				});
				if (e.isBegan()){
					display.asyncExec(new Runnable() {
						public void run() {							
							btnPause.setEnabled(true);
							btnCancel.setEnabled(true);
							btnCalculate.setEnabled(false);
							progressBar.setVisible(true);							

						}
					});
				}
				if (e.isTerminated()){
					display.asyncExec(new Runnable() {
						public void run() {
							textResult.setText(textResult.getText()+e.getResult()+"\r\n");
							btnPause.setEnabled(false);
							btnCancel.setEnabled(false);
							btnCalculate.setEnabled(true);
							progressBar.setVisible(false);
							lblPgbar.setText("");

						}
					});
				}

			}

		});		
		return task;
	}
}