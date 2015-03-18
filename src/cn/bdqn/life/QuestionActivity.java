package cn.bdqn.life;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
 * ����
 */
public class QuestionActivity extends Activity {

	private EditText ques_edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);

		ques_edit = (EditText) this.findViewById(R.id.questionEdit);
		TextView mSendButton = (TextView) this.findViewById(R.id.ques_send);
		TextView mCancelButton = (TextView) this.findViewById(R.id.ques_cancel);
		// ���Ͱ�ť
		mSendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String question = ques_edit.getText().toString();
				if ("".equals(question) || question == null) {
					Toast.makeText(QuestionActivity.this, "����Ϊ��!",
							Toast.LENGTH_LONG).show();
				} else {
					// �Ժ����Ʒ������ʼ�����������
					Toast.makeText(QuestionActivity.this,
							R.string.question_thank, Toast.LENGTH_LONG).show();
				}
			}
		});
		// ȡ����ť
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				QuestionActivity.this.finish();
			}
		});
	}
}
