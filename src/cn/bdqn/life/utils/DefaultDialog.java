package cn.bdqn.life.utils;


import cn.bdqn.life.R;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

/**Dailog»ùÀà*/
public abstract class DefaultDialog extends Builder {

	private String[] items;
	
	public DefaultDialog(Activity activity,String[] items,boolean flag) {
		super(activity);
		this.items = items;
		this.item(flag);
		
	}

	private void item(boolean flag) {
		if(flag){
			this.setPositiveButton(R.string.submit, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					doPositive();
				}
			});
		}
		this.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		this.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				doItems(which);
			}
		});
	}
	protected abstract void doPositive();
	protected abstract void doItems(int which);
}
