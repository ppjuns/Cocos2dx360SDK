/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.qihoo.gamecenter.sdk.activity.ContainerActivity;
import com.qihoo.gamecenter.sdk.common.IDispatcherCallback;
import com.qihoo.gamecenter.sdk.matrix.Matrix;
import com.qihoo.gamecenter.sdk.protocols.ProtocolConfigs;
import com.qihoo.gamecenter.sdk.protocols.ProtocolKeys;

public class AppActivity extends Cocos2dxActivity {
	private static Context mContext;
	private static String mAccess_token;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mContext = this;
		Matrix.init(this);
		
	}
	
	
	
	
	public static void  login360(){
		
		Intent intent = getLoginIntent();
		IDispatcherCallback callback = mLoginCallback;
		Matrix.execute(mContext, intent, callback);
		
	}
	
	//360login
		private static Intent getLoginIntent() {
			Intent intent = new Intent(mContext, ContainerActivity.class);
			// 必需参数，使用 360SDK 的登录模块
			intent.putExtra(ProtocolKeys.FUNCTION_CODE,
					ProtocolConfigs.FUNC_CODE_LOGIN);

			return intent;
		}
		
		private static IDispatcherCallback mLoginCallback = new IDispatcherCallback() {
			@Override
			public void onFinished(String data) {
				// press back
				if (isCancelLogin(data)) {
					// 失败
					return;
				}

				// 解析 access_token
			
				
				if (!TextUtils.isEmpty(mAccess_token)) {
					// 需要去应用的服务器获取用 access_token 获取一下用户信息
					// getUserInfo();
				Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();

				} else {
					// token过期

				}
			}
		};
		private static boolean isCancelLogin(String data) {
			try {
				JSONObject joData = new JSONObject(data);
				int errno = joData.optInt("errno", -1);
				if (-1 == errno) {

					return true;
				}
			} catch (Exception e) {
			}
			return false;
		}

}
