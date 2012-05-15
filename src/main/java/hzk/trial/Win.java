package hzk.trial;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Win {

	protected Shell shlMyswtdemoapp;
	private Text text;
	private ProgressBar progressBar;
	Display display;
	private Button btnAlert;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Win window = new Win();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlMyswtdemoapp.open();
		shlMyswtdemoapp.layout();
		while (!shlMyswtdemoapp.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlMyswtdemoapp = new Shell();
		shlMyswtdemoapp.setSize(450, 300);
		shlMyswtdemoapp.setText("MySWTDemoApp");
		text = new Text(shlMyswtdemoapp, SWT.BORDER);
		text.setBounds(10, 10, 267, 23);
		progressBar = new ProgressBar(shlMyswtdemoapp, SWT.NONE);
		progressBar.setBounds(10, 235, 414, 17);
		Button btnNewButton = new Button(shlMyswtdemoapp, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				final int maximum = progressBar.getMaximum();
				new Thread() {
					public void run() {

						for (final int[] i = new int[1]; i[0] <= maximum; i[0]++) {
							try {
								Thread.sleep(100);
							} catch (Throwable th) {
							}
							if (display.isDisposed())
								return;
							display.asyncExec(new Runnable() {
								public void run() {
									if (progressBar.isDisposed())
										return;
									progressBar.setSelection(i[0]);
								}
							});
						}
					}
				}.start();
			}

		});

		btnNewButton.setBounds(344, 10, 80, 27);
		btnNewButton.setText("Go");

		Button btnExit = new Button(shlMyswtdemoapp, SWT.NONE);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shlMyswtdemoapp.close();
			}
		});
		btnExit.setBounds(344, 43, 80, 27);
		btnExit.setText("Exit");

		btnAlert = new Button(shlMyswtdemoapp, SWT.NONE);
		btnAlert.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				MessageBox box = new MessageBox(shlMyswtdemoapp, SWT.ICON_SEARCH);
				box.setMessage(text.getText());
				box.open();
			}
		});
		btnAlert.setBounds(344, 76, 80, 27);
		btnAlert.setText("Alert");

	}
}
