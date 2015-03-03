package com.accident.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

import com.accident.app.dbhelper.DBhelper;
import com.accident.app.util.Config;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{
    /* Your Tab host */
    private TabHost mTabHost;

    /* A HashMap of stacks, where we use tab identifier as keys..*/
    private HashMap<String, Stack<Fragment>> mStacks;

    /*Save current tabs identifier in this..*/
    private String mCurrentTab;
    DBhelper dBhelper;
    //header
    public TextView HeadingText;
    ImageView mHomeIcon,mMenu,mTowing,mSend,mEdit,mSave,mClose,mDelete;
    
    
    ArrayList<HashMap<String,Object>> lst = new ArrayList<HashMap<String,Object>>();
    public RelativeLayout homeLayour, ScreensLayout;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dBhelper = new DBhelper(this);
        
        homeLayour = (RelativeLayout)findViewById(R.id.home_header);
        ScreensLayout = (RelativeLayout)findViewById(R.id.screens_header);
        
        //Header
        HeadingText = (TextView)findViewById(R.id.heading);
        mHomeIcon = (ImageView)findViewById(R.id.home);
        mMenu = (ImageView)findViewById(R.id.menu); 
        mTowing = (ImageView)findViewById(R.id.towing);
        mSend = (ImageView)findViewById(R.id.send);
        mEdit = (ImageView)findViewById(R.id.edit);
        mSave = (ImageView)findViewById(R.id.save_right);
        mClose = (ImageView)findViewById(R.id.close);
        mDelete = (ImageView)findViewById(R.id.delete);
        
        mHomeIcon.setOnClickListener(this);
        mMenu.setOnClickListener(this); 
        mTowing.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mSave.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        
        CallReportTable();
        /*  
         *  Navigation stacks for each tab gets created.. 
         *  tab identifier is used as key to get respective stack for each tab
         */
        
        mStacks             =   new HashMap<String, Stack<Fragment>>();
        mStacks.put(AppConstants.TAB_DETAILS, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_LOCATION, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_PICTURES, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_IMAGES, new Stack<Fragment>());

        mTabHost                =   (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();

        initializeTabs();
    }

    private void CallReportTable(){
    	String Date = GetCurrentDate();
    	dBhelper.insertReport(Date, isUpdate(Date));
    }
    
    public String GetCurrentDate(){
    	Calendar c = Calendar.getInstance(); 
    	return (""+c.get(Calendar.DAY_OF_MONTH)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.YEAR));
    	//return (""+1+"-"+3+"-"+2015);
    }
    
    public int getIds(){
    	lst.clear();
    	lst = dBhelper.getData(dBhelper.TABLE_NAME_REPORT);
    	String Date = GetCurrentDate();
    	for(int i=0;i<lst.size();i++){
    		String s = lst.get(i).get(AppConstants.ITEM1).toString();
    		if(Date.equals(s))
    			return (Integer) lst.get(i).get(AppConstants.ITEM0);
    	}
    	return 5;
    }

    private boolean isUpdate(String Date){
		lst = dBhelper.getData(dBhelper.TABLE_NAME_REPORT);
		for(int i=0;i<lst.size();i++){
		String s = lst.get(i).get(AppConstants.ITEM1).toString();
		if(Date.equals(s))
			return true;
		}
		return false;
	}
    private View createTabView(final int id,String text) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView =   (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));
        return view;
    }

    public void initializeTabs(){
        /* Setup your tab icons and content views.. Nothing special in this..*/
        TabHost.TabSpec spec    =   mTabHost.newTabSpec(AppConstants.TAB_DETAILS);
        mTabHost.setCurrentTab(-3);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.detail_selector,""));
        mTabHost.addTab(spec);

        //Search

        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_LOCATION);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.location_selector,""));
        mTabHost.addTab(spec);
        
        
        //Recents
        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_PICTURES);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.picture_selector,""));
        mTabHost.addTab(spec);
        
        
        //Contacts
        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_IMAGES);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(R.drawable.damage_selector,""));
        mTabHost.addTab(spec);
       
       
    }
    
    /*Comes here when user switch tab, or we do programmatically*/
    TabHost.OnTabChangeListener listener    =   new TabHost.OnTabChangeListener() {
      public void onTabChanged(String tabId) {
    	
    		//if(selectedTabTextView != null)
				//selectedTabTextView.setTextColor(Color.BLACK);
    		
      //  View mView = mTabHost.getTabWidget().getChildTabViewAt(mIndexMap.get(tabId));
		//selectedTabTextView = (ImageView)mView.findViewById(R.id.textView1);
		//selectedTabTextView.setTextColor(Color.BLUE);
		
        /*Set current tab..*/
        mCurrentTab                     =   tabId;


        if(mStacks.get(tabId).size() == 0){
          /*
           *    First time this tab is selected. So add first fragment of that tab.
           *    Dont need animation, so that argument is false.
           *    We are adding a new fragment which is not present in stack. So add to stack is true.
           */
          if(tabId.equals(AppConstants.TAB_DETAILS)){
            pushFragments(tabId, new GridFragment(), false,true);
          }else if(tabId.equals(AppConstants.TAB_LOCATION)){
            pushFragments(tabId, new LocationFragment(), false,true);
          }
          else if(tabId.equals(AppConstants.TAB_PICTURES)){
              pushFragments(tabId, new CameraImage(), false,true);
            }
          else if(tabId.equals(AppConstants.TAB_IMAGES)){
              pushFragments(tabId, new DummySectionFragment(), false,true);
            }
        }else {
          /*
           *    We are switching tabs, and target tab is already has atleast one fragment. 
           *    No need of animation, no need of stack pushing. Just show the target fragment
           */
          pushFragments(tabId, mStacks.get(tabId).lastElement(), false,false);
        }
      }
    };


    /* Might be useful if we want to switch tab programmatically, from inside any of the fragment.*/
    public void setCurrentTab(int val){
          mTabHost.setCurrentTab(val);
    }


    /* 
     *      To add fragment to a tab. 
     *  tag             ->  Tab identifier
     *  fragment        ->  Fragment to show, in tab identified by tag
     *  shouldAnimate   ->  should animate transaction. false when we switch tabs, or adding first fragment to a tab
     *                      true when when we are pushing more fragment into navigation stack. 
     *  shouldAdd       ->  Should add to fragment navigation stack (mStacks.get(tag)). false when we are switching tabs (except for the first time)
     *                      true in all other cases.
     */
    public void pushFragments(String tag, Fragment fragment,boolean shouldAnimate, boolean shouldAdd){
      if(shouldAdd)
          mStacks.get(tag).push(fragment);
      FragmentManager   manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      if(shouldAnimate)
          ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }


    public void popFragments(){
      /*    
       *    Select the second last fragment in current tab's stack.. 
       *    which will be shown after the fragment transaction given below 
       */
      Fragment fragment             =   mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

      /*pop current fragment from stack.. */
      mStacks.get(mCurrentTab).pop();

      /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
      FragmentManager   manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }   


    @Override
    public void onBackPressed() {
       	if(((BaseFragment)mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false){
       		/*
       		 * top fragment in current tab doesn't handles back press, we can do our thing, which is
       		 * 
       		 * if current tab has only one fragment in stack, ie first fragment is showing for this tab.
       		 *        finish the activity
       		 * else
       		 *        pop to previous fragment in stack for the same tab
       		 * 
       		 */
       		if(mStacks.get(mCurrentTab).size() == 1){
       			super.onBackPressed();  // or call finish..
       		}else{
       			popFragments();
       		}
       	}else{
       		//do nothing.. fragment already handled back button press.
       	}
    }


    /*
     *   Imagine if you wanted to get an image selected using ImagePicker intent to the fragment. Ofcourse I could have created a public function
     *  in that fragment, and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mStacks.get(mCurrentTab).size() == 0){
            return;
        }

        /*Now current fragment on screen gets onActivityResult callback..*/
        mStacks.get(mCurrentTab).lastElement().onActivityResult(requestCode, resultCode, data);
    }
    
    public void CallHeaderVisiblity(){
    	if(AppConstants.isFront){
    		homeLayour.setVisibility(View.VISIBLE);
    		ScreensLayout.setVisibility(View.GONE);
    	}else{
    		ScreensLayout.setVisibility(View.VISIBLE);
    		homeLayour.setVisibility(View.GONE);
    	}
    }

	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.home:
		//CallHomeButton();
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
		CallSaveButton();
		break;		
	case R.id.close:
		CallCloseButton();
		break;	
	case R.id.delete:
		CallDeleteButton();
		break;			
		}
	}

	
	private void CallHomeButton(){	
	if(!mCurrentTab.equals(AppConstants.TAB_DETAILS))
		pushFragments(AppConstants.TAB_DETAILS, new GridFragment(), false,true);
	else
		Toast.makeText(MainActivity.this, "Currently at Home", Toast.LENGTH_SHORT).show();
	}
	
private void CallMenuButton(){
	
	 PopupMenu popup = new PopupMenu(MainActivity.this, mMenu);  
     //Inflating the Popup using xml file  
     popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());  
    
     //registering popup with OnMenuItemClickListener  
     popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
      public boolean onMenuItemClick(MenuItem item) {  
       Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();  
       return true;  
      }  
     });  

     popup.show();//showing popup menu  
}

private void CallTowingButton(){
	
	final Dialog dialog = new Dialog(MainActivity.this);
	dialog.setContentView(R.layout.towing_window);
	dialog.setTitle("Towing");

	// set the custom dialog components - text, image and button
	EditText dateTowing = (EditText) dialog.findViewById(R.id.date_towing);
	Button GetLocation = (Button) dialog.findViewById(R.id.get_location);
	Button Save = (Button) dialog.findViewById(R.id.save_towing);
	Button Cancel = (Button) dialog.findViewById(R.id.cancel_towing);

	GetLocation.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.dismiss();
		}
	});
	
	Save.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.dismiss();
		}
	});
	
	// if button is clicked, close the custom dialog
	Cancel.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.dismiss();
		}
	});

	dialog.show();
}
	


private void CallSendButton(){
	
}

private void CallEditButton(){
	ArrayList<HashMap<String,Object>> contantList = dBhelper.getData(dBhelper.TABLE_NAME_IMAGE_PATH);
	Log.e("Working", "size is "+contantList.size());
	for(int i=0;i<contantList.size();i++)
	Log.e("Images", contantList.get(0).get(AppConstants.ITEM1).toString());
}

private void CallSaveButton(){
	Document doc = new Document();
	
	 try {
		 
		/* String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+
				 		Config.IMAGE_DIRECTORY_NAME;*/
		 
		 String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/droidText";
		 	File dir = new File(path);
		        if(!dir.exists())
		        	dir.mkdirs();

		    Log.d("PDFCreator", "PDF Path: " + path);
		    
		   /* String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
					Locale.getDefault()).format(new Date());
		        
		    File file = new File(dir,"DOC_"+timeStamp+".pdf");*/
		    
		    File file = new File(dir,"sample.pdf");
		    FileOutputStream fOut = new FileOutputStream(file);

   	 	PdfWriter.getInstance(doc, fOut);
            
           //open the document
           doc.open();
           
           
           Paragraph p1 = new Paragraph("Hi! I am generating my first PDF");
           Font paraFont= new Font(Font.COURIER);
           p1.setAlignment(Paragraph.ALIGN_CENTER);
           p1.setFont(paraFont);
           
            //add paragraph to document    
            doc.add(p1);
           
            Paragraph p2 = new Paragraph("This is an example of a simple paragraph");
            Font paraFont2= new Font(Font.COURIER,14.0f,Color.GREEN);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(paraFont2);
            
            doc.add(p2);
            
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.ic_launcher);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);
           
            //add image to document
            doc.add(myImg);
           
            //set footer
            Phrase footerText = new Phrase("This is an example of a footer");
            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
            doc.setFooter(pdfFooter);
           

           
    } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
    } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
    } 
	 finally
    {
            doc.close();
    }
}

private void CallCloseButton(){
	
}

private void CallDeleteButton(){
	
}
	
	
}
