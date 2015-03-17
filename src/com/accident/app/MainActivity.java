package com.accident.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

import com.accident.app.dbhelper.DBhelper;
import com.accident.app.util.Config;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.lowagie.text.pdf.draw.VerticalPositionMark;

import android.R.style;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	/* Your Tab host */
	private TabHost mTabHost;

	/* A HashMap of stacks, where we use tab identifier as keys.. */
	private HashMap<String, Stack<Fragment>> mStacks;

	/* Save current tabs identifier in this.. */
	private String mCurrentTab;
	DBhelper dBhelper;
	// header
	ImageView mHomeIcon, mMenu, mTowing, mSend, mEdit, mSave;
	int currentID;

	ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
	public RelativeLayout homeLayour;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dBhelper = new DBhelper(this);

		homeLayour = (RelativeLayout) findViewById(R.id.home_header);

		// Header

		mHomeIcon = (ImageView) findViewById(R.id.home);
		mMenu = (ImageView) findViewById(R.id.menu);
		mTowing = (ImageView) findViewById(R.id.towing);
		mSend = (ImageView) findViewById(R.id.send);
		mEdit = (ImageView) findViewById(R.id.edit);
		mSave = (ImageView) findViewById(R.id.save_right);

		mHomeIcon.setOnClickListener(this);
		mMenu.setOnClickListener(this);
		mTowing.setOnClickListener(this);
		mSend.setOnClickListener(this);
		mEdit.setOnClickListener(this);
		mSave.setOnClickListener(this);

		CallReportTable();
		/*
		 * Navigation stacks for each tab gets created.. tab identifier is used
		 * as key to get respective stack for each tab
		 */

		mStacks = new HashMap<String, Stack<Fragment>>();
		mStacks.put(AppConstants.TAB_DETAILS, new Stack<Fragment>());
		mStacks.put(AppConstants.TAB_LOCATION, new Stack<Fragment>());
		mStacks.put(AppConstants.TAB_PICTURES, new Stack<Fragment>());
		mStacks.put(AppConstants.TAB_IMAGES, new Stack<Fragment>());

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();

		initializeTabs();
	}

	private void CallReportTable() {
		String Date = GetCurrentDate();
		dBhelper.insertReport(Date, isUpdate(Date));

	}

	public String GetCurrentDate() {
		Calendar c = Calendar.getInstance();
		return ("" + c.get(Calendar.DAY_OF_MONTH) + "-"
				+ (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR));
		// return (""+1+"-"+3+"-"+2015);
	}

	public int getIds() {
		lst.clear();
		lst = dBhelper.getData(dBhelper.TABLE_NAME_REPORT);
		String Date = GetCurrentDate();
		for (int i = 0; i < lst.size(); i++) {
			String s = lst.get(i).get(AppConstants.ITEM1).toString();
			if (Date.equals(s))
				return (Integer) lst.get(i).get(AppConstants.ITEM0);
		}
		return -1;
	}

	private boolean isUpdate(String Date) {
		lst = dBhelper.getData(dBhelper.TABLE_NAME_REPORT);
		for (int i = 0; i < lst.size(); i++) {
			String s = lst.get(i).get(AppConstants.ITEM1).toString();
			if (Date.equals(s))
				return true;
		}
		return false;
	}

	private View createTabView(final int id, String text) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		imageView.setImageDrawable(getResources().getDrawable(id));
		return view;
	}

	public void initializeTabs() {
		/* Setup your tab icons and content views.. Nothing special in this.. */
		TabHost.TabSpec spec = mTabHost.newTabSpec(AppConstants.TAB_DETAILS);
		mTabHost.setCurrentTab(-3);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.detail_selector, ""));
		mTabHost.addTab(spec);

		// Search

		spec = mTabHost.newTabSpec(AppConstants.TAB_LOCATION);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.location_selector, ""));
		mTabHost.addTab(spec);

		// Recents
		spec = mTabHost.newTabSpec(AppConstants.TAB_PICTURES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.picture_selector, ""));
		mTabHost.addTab(spec);

		// Contacts
		spec = mTabHost.newTabSpec(AppConstants.TAB_IMAGES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.damage_selector, ""));
		mTabHost.addTab(spec);

	}

	/* Comes here when user switch tab, or we do programmatically */
	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {

			// if(selectedTabTextView != null)
			// selectedTabTextView.setTextColor(Color.BLACK);

			// View mView =
			// mTabHost.getTabWidget().getChildTabViewAt(mIndexMap.get(tabId));
			// selectedTabTextView =
			// (ImageView)mView.findViewById(R.id.textView1);
			// selectedTabTextView.setTextColor(Color.BLUE);

			/* Set current tab.. */
			mCurrentTab = tabId;

			if (mStacks.get(tabId).size() == 0) {
				/*
				 * First time this tab is selected. So add first fragment of
				 * that tab. Dont need animation, so that argument is false. We
				 * are adding a new fragment which is not present in stack. So
				 * add to stack is true.
				 */
				if (tabId.equals(AppConstants.TAB_DETAILS)) {
					pushFragments(tabId, new GridFragment(), false, true);
				} else if (tabId.equals(AppConstants.TAB_LOCATION)) {
					pushFragments(tabId, new LocationFragment(), false, true);
				} else if (tabId.equals(AppConstants.TAB_PICTURES)) {
					pushFragments(tabId, new CameraImage(), false, true);
				} else if (tabId.equals(AppConstants.TAB_IMAGES)) {
					pushFragments(tabId, new DummySectionFragment(), false,
							true);
				}
			} else {
				/*
				 * We are switching tabs, and target tab is already has atleast
				 * one fragment. No need of animation, no need of stack pushing.
				 * Just show the target fragment
				 */
				pushFragments(tabId, mStacks.get(tabId).lastElement(), false,
						false);
			}
		}
	};

	/*
	 * Might be useful if we want to switch tab programmatically, from inside
	 * any of the fragment.
	 */
	public void setCurrentTab(int val) {
		mTabHost.setCurrentTab(val);
	}

	/*
	 * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to
	 * show, in tab identified by tag shouldAnimate -> should animate
	 * transaction. false when we switch tabs, or adding first fragment to a tab
	 * true when when we are pushing more fragment into navigation stack.
	 * shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)).
	 * false when we are switching tabs (except for the first time) true in all
	 * other cases.
	 */
	public void pushFragments(String tag, Fragment fragment,
			boolean shouldAnimate, boolean shouldAdd) {
		if (shouldAdd)
			mStacks.get(tag).push(fragment);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (shouldAnimate)
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	public void popFragments() {
		/*
		 * Select the second last fragment in current tab's stack.. which will
		 * be shown after the fragment transaction given below
		 */
		Fragment fragment = mStacks.get(mCurrentTab).elementAt(
				mStacks.get(mCurrentTab).size() - 2);

		/* pop current fragment from stack.. */
		mStacks.get(mCurrentTab).pop();

		/*
		 * We have the target fragment in hand.. Just show it.. Show a standard
		 * navigation animation
		 */
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		handleBackPressed();
	}

	public void handleBackPressed() {
		if (((BaseFragment) mStacks.get(mCurrentTab).lastElement())
				.onBackPressed() == false) {
			/*
			 * top fragment in current tab doesn't handles back press, we can do
			 * our thing, which is
			 * 
			 * if current tab has only one fragment in stack, ie first fragment
			 * is showing for this tab. finish the activity else pop to previous
			 * fragment in stack for the same tab
			 */
			if (mStacks.get(mCurrentTab).size() == 1) {
				super.onBackPressed(); // or call finish..
			} else {
				popFragments();
			}
		} else {
			// do nothing.. fragment already handled back button press.
		}
	}

	/*
	 * Imagine if you wanted to get an image selected using ImagePicker intent
	 * to the fragment. Ofcourse I could have created a public function in that
	 * fragment, and called it from the activity. But couldn't resist myself.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (mStacks.get(mCurrentTab).size() == 0) {
			return;
		}

		/* Now current fragment on screen gets onActivityResult callback.. */
		mStacks.get(mCurrentTab).lastElement()
				.onActivityResult(requestCode, resultCode, data);
	}

	public void CallHeaderVisiblity() {
		if (AppConstants.isFront) {
			homeLayour.setVisibility(View.VISIBLE);
		} else {
			homeLayour.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home:
			CallHomeButton();
			break;

		case R.id.menu:
			CallMenuButton();
			break;
		case R.id.towing:
			CallTowingButton();
			break;
		case R.id.send:
			CallSendButton();
			break;
		case R.id.edit:
			CallEditButton();
			break;
		case R.id.save_right:
			Toast.makeText(MainActivity.this, "Pdf Creating",
					Toast.LENGTH_SHORT).show();
			CallSaveButton();
			break;
		}
	}

	private void CallHomeButton() {
		if (!mCurrentTab.equals(AppConstants.TAB_DETAILS)) {
			mTabHost.setCurrentTab(0);
		} else
			Toast.makeText(MainActivity.this, "Currently at Home",
					Toast.LENGTH_SHORT).show();
	}

	private void CallMenuButton() {

		PopupMenu popup = new PopupMenu(MainActivity.this, mMenu);
		// Inflating the Popup using xml file
		popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

		// registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(MainActivity.this,
						"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

		popup.show();// showing popup menu

		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				switch (item.getItemId()) {
				case R.id.my_details:
					Toast.makeText(MainActivity.this, "My Details",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.history:
					Toast.makeText(MainActivity.this, "History",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.language:
					Toast.makeText(MainActivity.this, "Language",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.tell_a_friend:
					Toast.makeText(MainActivity.this, "Tell a friend",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.about_us:
					Toast.makeText(MainActivity.this, "AboutUs",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.exit_application:
					Toast.makeText(MainActivity.this, "Exit_Application",
							Toast.LENGTH_SHORT).show();
					break;

				}

				return false;
			}
		});
	}

	private void CallTowingButton() {

		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.towing_window);
		// dialog.setTitle("Towing");
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		// set the custom dialog components - text, image and button
		EditText PhoneTwoing = (EditText) dialog
				.findViewById(R.id.phone_towing);
		EditText GetLocation = (EditText) dialog
				.findViewById(R.id.get_location);
		Button yes = (Button) dialog.findViewById(R.id.yes_towing);
		Button no = (Button) dialog.findViewById(R.id.no_towing);

		if (!AppConstants.address.equals(""))
			GetLocation.setText(AppConstants.address);

		yes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		// if button is clicked, close the custom dialog
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	private void CallSendButton() {

		PopupMenu popup = new PopupMenu(MainActivity.this, mMenu);
		// Inflating the Popup using xml file
		popup.getMenuInflater().inflate(R.menu.send_menu, popup.getMenu());

		// registering popup with OnMenuItemClickListener
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				Toast.makeText(MainActivity.this,
						"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});

		popup.show();// showing popup menu

		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {

				switch (item.getItemId()) {
				case R.id.my_details:
					Toast.makeText(MainActivity.this, "My Details",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.history:
					Toast.makeText(MainActivity.this, "History",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.language:
					Toast.makeText(MainActivity.this, "Language",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.tell_a_friend:
					Toast.makeText(MainActivity.this, "Tell a friend",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.about_us:
					Toast.makeText(MainActivity.this, "AboutUs",
							Toast.LENGTH_SHORT).show();
					break;
				case R.id.exit_application:
					Toast.makeText(MainActivity.this, "Exit_Application",
							Toast.LENGTH_SHORT).show();
					break;

				}

				return false;
			}
		});

	}

	private void CallEditButton() {

		try {

			if (!AppConstants.fullpath.equals(null)) {
				File file = new File(AppConstants.fullpath);

				if (file.exists()) {
					Uri path = Uri.fromFile(file);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(path, "application/pdf");
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					try {
						startActivity(intent);
					} catch (ActivityNotFoundException e) {
						Toast.makeText(MainActivity.this,
								"No Application Available to View PDF",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(MainActivity.this, "Not Exists",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(
						MainActivity.this,
						"First Create Pdf or see old pdf in AccidentalReport Folder in memory",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(
					MainActivity.this,
					"First Create Pdf or see old pdf in AccidentalReport Folder in memory",
					Toast.LENGTH_LONG).show();
		}

	}

	private void CallSaveButton() {

		Document doc = new Document();
		currentID = getIds();
		lst.clear();
		try {

			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/AccidentalReport";
			File dir = new File(path);
			if (!dir.exists())
				dir.mkdirs();

			Log.d("PDFCreator", "PDF Path: " + path);

			String timeStamp = "DOC_"
					+ new SimpleDateFormat("yyyyMMdd_HHmmss",
							Locale.getDefault()).format(new Date()) + ".pdf";

			AppConstants.fullpath = path + "/" + timeStamp;
			File file = new File(dir, timeStamp);
			/*
			 * if(file.exists()) file.delete();
			 */

			FileOutputStream fOut = new FileOutputStream(file);

			PdfWriter.getInstance(doc, fOut);
			// doc.addAuthor("Me");
			// doc.addTitle("My iText Test");

			// set header
			/*
			 * Phrase headerText = new Phrase("This is an example of a Header");
			 * HeaderFooter pdfHeader = new HeaderFooter(headerText, false);
			 * doc.setHeader(pdfHeader);
			 */

			// open the document
			doc.open();

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext()
					.getResources(), R.drawable.logo);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			Image myImg = Image.getInstance(stream.toByteArray());
			myImg.setAlignment(Image.LEFT);

			// add image to document
			doc.add(myImg);

			/*
			 * Chunk CONNECT = new Chunk( new LineSeparator(0.5f, 95,
			 * harmony.java.awt.Color.BLUE, Element.ALIGN_CENTER, 3.5f)); Chunk
			 * tab1 = new Chunk(new VerticalPositionMark(), 200, true);
			 */

			LineSeparator UNDERLINE = new LineSeparator(2, 100, null,
					Element.ALIGN_CENTER, -2);
			LineSeparator UNDERLINE2 = new LineSeparator(1, 100, null,
					Element.ALIGN_CENTER, -2);

			Chunk chunk = new Chunk("             Accident Report");
			Font font = new Font(Font.COURIER, 14.0f, Color.RED);
			font.setStyle(Font.UNDERLINE);
			font.setStyle(Font.BOLD);
			font.setSize(30.0f);
			// chunk.setBackground(harmony.java.awt.Color.RED);
			chunk.setFont(font);
			// chunk.setBackground(null, Color.CYAN, currentID, currentID,
			// currentID);
			doc.add(chunk);

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_DATE_TIME, currentID);
			if (lst.size() > 0) {
				Paragraph p2 = new Paragraph("Written on "
						+ lst.get(0).get(AppConstants.ITEM1) + ", "
						+ lst.get(0).get(AppConstants.ITEM2));
				Font paraFont2 = new Font(Font.COURIER, 14.0f, Color.BLACK);
				p2.setAlignment(Paragraph.ALIGN_LEFT);
				p2.setFont(paraFont2);
				p2.add(UNDERLINE);
				doc.add(p2);
			}

			Font paraFont2 = new Font(Font.COURIER, 14.0f, Color.BLACK);
			Font paraFont3 = new Font(Font.COURIER, 14.0f, Color.BLACK);
			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_POLICE, currentID);
			if (lst.size() > 0) {
				Paragraph p61 = new Paragraph("Police details");

				p61.setAlignment(Paragraph.ALIGN_LEFT);
				p61.setFont(paraFont2);
				p61.add(UNDERLINE2);
				// p61.add(new Chunk(tab1));
				doc.add(p61);

				Paragraph p6 = new Paragraph(// "Police details"+"\n"+
						"Event number : " + lst.get(0).get(AppConstants.ITEM1)
								+ "\n" + "Case number : "
								+ lst.get(0).get(AppConstants.ITEM2) + "\n"
								+ "Unit name : "
								+ lst.get(0).get(AppConstants.ITEM3) + "\n"
								+ "Station name : "
								+ lst.get(0).get(AppConstants.ITEM4));
				p6.setAlignment(Paragraph.ALIGN_LEFT);
				p6.setFont(paraFont3);
				doc.add(p6);
			}

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_THIRD_PARTY, currentID);
			if (lst.size() > 0) {
				Paragraph p63 = new Paragraph("Third party details");
				p63.setAlignment(Paragraph.ALIGN_LEFT);
				p63.setFont(paraFont2);
				p63.add(UNDERLINE2);
				// p63.add(CONNECT);
				doc.add(p63);
				String owner;
				if (((Integer) (lst.get(0).get(AppConstants.ITEM6))) == 1) {
					owner = "The driver is the vehicle owner";
				} else {
					owner = "The driver is not the vehicle owner";
				}
				Paragraph p3 = new Paragraph("Driver name : "
						+ lst.get(0).get(AppConstants.ITEM1) + "\n" + "Id : "
						+ lst.get(0).get(AppConstants.ITEM2) + "\n"
						+ "Address : " + lst.get(0).get(AppConstants.ITEM3)
						+ "\n" + "Phone number : "
						+ lst.get(0).get(AppConstants.ITEM4) + "\n"
						+ "License number : "
						+ lst.get(0).get(AppConstants.ITEM5) + "\n" + owner);
				// Font paraFont3= new Font(Font.COURIER,14.0f,Color.BLACK);
				p3.setAlignment(Paragraph.ALIGN_LEFT);
				p3.setFont(paraFont3);
				doc.add(p3);
			}

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_VEHICLE, currentID);
			if (lst.size() > 0) {
				Paragraph p64 = new Paragraph("Vehicle details");
				p64.setAlignment(Paragraph.ALIGN_LEFT);
				p64.setFont(paraFont2);
				p64.add(UNDERLINE2);
				doc.add(p64);

				Paragraph p4 = new Paragraph("Vehicle type : "
						+ lst.get(0).get(AppConstants.ITEM1) + "\n"
						+ "Manufacturer : "
						+ lst.get(0).get(AppConstants.ITEM2) + "\n"
						+ "Model : " + lst.get(0).get(AppConstants.ITEM3)
						+ "\n" + "Color : "
						+ lst.get(0).get(AppConstants.ITEM4) + "\n" + "Year : "
						+ lst.get(0).get(AppConstants.ITEM5) + "\n"
						+ "License plate : "
						+ lst.get(0).get(AppConstants.ITEM6));
				// Font paraFont3= new Font(Font.COURIER,14.0f,Color.BLACK);
				p4.setAlignment(Paragraph.ALIGN_LEFT);
				p4.setFont(paraFont3);
				doc.add(p4);
			}

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_INSURANCE, currentID);
			if (lst.size() > 0) {
				Paragraph p65 = new Paragraph("Insurace details");
				p65.setAlignment(Paragraph.ALIGN_LEFT);
				p65.setFont(paraFont2);
				p65.add(UNDERLINE2);
				doc.add(p65);

				Paragraph p5 = new Paragraph("Agency name : "
						+ lst.get(0).get(AppConstants.ITEM1) + "\n"
						+ "Policy number : "
						+ lst.get(0).get(AppConstants.ITEM2) + "\n"
						+ "Agent name : " + lst.get(0).get(AppConstants.ITEM3)
						+ "\n" + "Agent number : "
						+ lst.get(0).get(AppConstants.ITEM4));
				// Font paraFont3= new Font(Font.COURIER,14.0f,Color.BLACK);
				p5.setAlignment(Paragraph.ALIGN_LEFT);
				p5.setFont(paraFont3);
				doc.add(p5);
			}

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_DESCRIPTION, currentID);
			if (lst.size() > 0) {
				Paragraph p67 = new Paragraph("Accident description");
				p67.setAlignment(Paragraph.ALIGN_LEFT);
				p67.setFont(paraFont2);
				p67.add(UNDERLINE2);
				doc.add(p67);

				Paragraph p7 = new Paragraph(""
						+ lst.get(0).get(AppConstants.ITEM1));
				// Font paraFont3= new Font(Font.COURIER,14.0f,Color.BLACK);
				p7.setAlignment(Paragraph.ALIGN_LEFT);
				p7.setFont(paraFont3);
				doc.add(p7);
			}

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_WITNESSES, currentID);
			if (lst.size() > 0) {
				Paragraph p68 = new Paragraph("Witnesses details");
				p68.setAlignment(Paragraph.ALIGN_LEFT);
				p68.setFont(paraFont2);
				p68.add(UNDERLINE2);
				doc.add(p68);
				Paragraph p8;
				for (int i = 0; i < lst.size(); i++) {
					p8 = new Paragraph("[" + lst.size() + "]"
							+ lst.get(i).get(AppConstants.ITEM1) + "\n"
							+ "Id : " + lst.get(i).get(AppConstants.ITEM2)
							+ "\n" + "Address : "
							+ lst.get(i).get(AppConstants.ITEM3) + "\n"
							+ "Phone number : "
							+ lst.get(i).get(AppConstants.ITEM4) + "\n"
							+ "License number : "
							+ lst.get(i).get(AppConstants.ITEM5));
					// Font paraFont3= new Font(Font.COURIER,14.0f,Color.BLACK);
					p8.setAlignment(Paragraph.ALIGN_LEFT);
					p8.setFont(paraFont3);
					doc.add(p8);
				}
			}

			lst.clear();
			lst = dBhelper.getData(dBhelper.TABLE_NAME_CASUALTIES, currentID);
			if (lst.size() > 0) {
				Paragraph p69 = new Paragraph("Casualties details");
				p69.setAlignment(Paragraph.ALIGN_LEFT);
				p69.setFont(paraFont2);
				p69.add(UNDERLINE2);
				doc.add(p69);

				for (int i = 0; i < lst.size(); i++) {
					Paragraph p9;
					String Hospitlized;
					if (((Integer) (lst.get(i).get(AppConstants.ITEM6))) == 1) {
						Hospitlized = "The casualty was taken to hospital";
					} else {
						Hospitlized = "The Casualty was not taken to hospital";
					}
					p9 = new Paragraph("[" + lst.size() + "]"
							+ lst.get(i).get(AppConstants.ITEM1) + "\n"
							+ "Id : " + lst.get(i).get(AppConstants.ITEM2)
							+ "\n" + "Address : "
							+ lst.get(i).get(AppConstants.ITEM3) + "\n"
							+ "Phone number : "
							+ lst.get(i).get(AppConstants.ITEM4) + "\n"
							+ "License number : "
							+ lst.get(i).get(AppConstants.ITEM5) + "\n"
							+ Hospitlized);

					// Font paraFont3= new Font(Font.COURIER,14.0f,Color.BLACK);
					p9.setAlignment(Paragraph.ALIGN_LEFT);
					p9.setFont(paraFont3);
					doc.add(p9);
				}
			}

			if (AppConstants.bitmap0 != null) {
				Log.e("Mpa", "Creating start");
				doc.newPage();
				Paragraph p10 = new Paragraph("Accident location: "
						+ AppConstants.address);
				Font paraFont10 = new Font(Font.COURIER, 18.0f, Color.BLACK);
				p10.setAlignment(Paragraph.ALIGN_LEFT);
				p10.setFont(paraFont10);
				p10.add(UNDERLINE);
				doc.add(p10);

				ByteArrayOutputStream stream0 = new ByteArrayOutputStream();
				AppConstants.bitmap0.compress(Bitmap.CompressFormat.JPEG, 100,
						stream0);
				Image myImg0 = Image.getInstance(stream0.toByteArray());
				myImg0.setAlignment(Image.LEFT);
				// add image to document
				doc.add(myImg0);
				Log.e("Mpa", "Creating end");
			} else {
				Log.e("Mpa", "Not Creating");
			}

			lst.clear();
			// ArrayList<HashMap<String,Object>> contantList
			lst = dBhelper.getData(dBhelper.TABLE_NAME_IMAGE_PATH);
			// Log.e("Working", "size is "+contantList.size());
			if (lst.size() > 0) {
				for (int i = 0; i < lst.size(); i++) {
					doc.newPage();
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inSampleSize = 6;
					ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
					Bitmap bitmap2 = BitmapFactory.decodeFile(
							lst.get(i).get(AppConstants.ITEM1).toString(), opt);
					// decodeResource (getBaseContext().getResources(),
					// lst.get(i).get(AppConstants.ITEM1).toString());
					bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
					Image myImg2 = Image.getInstance(stream2.toByteArray());
					myImg2.setAlignment(Image.LEFT);

					// add image to document
					doc.add(myImg2);
				}
			}

			// set footer
			/*
			 * Phrase footerText = new Phrase("This is an example of a footer");
			 * HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
			 * doc.setFooter(pdfFooter);
			 */

		} catch (DocumentException de) {
			Log.e("PDFCreator", "DocumentException:" + de);
		} catch (IOException e) {
			Log.e("PDFCreator", "ioException:" + e);
		} finally {
			doc.close();
		}
		Toast.makeText(MainActivity.this, "Pdf Created", Toast.LENGTH_SHORT)
				.show();
	}

	/*
	 * private ArrayList<HashMap<String,Object>> GetDataFormDatabase(){ return
	 * lst;}
	 */

}
