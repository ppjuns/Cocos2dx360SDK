#### 1.概述 ####
U3D游戏和Cocos2dx手游都会接入不同渠道的支付方式，本文运用jni知识以360渠道接入作为演示。

#### 2.环境 ####
OS: win10
IDE: Eclipse
Adnroid NDK:r10e
cocos2d-x-3.9

#### 3.创建Cocos2d-x工程####
运行命令

    cocos new -p com.ohj.cocos2dx360 -l cpp -d Cocos2dx360SDK

然后进入Cocos2dx360SDK-MyCppGame-proj.android执行build_native.py 生成.so文件

编译成功后你会在libs-armeabi看到libcocos2dcpp.so 

最后导入Eclipse，是导入2个工程 ，分别是MyCppGame和库libcocos2dx。（导入EC时注意不要复制工程项目到工作区间）

#### 4.开始配置360SDK ####
导入assets、lib文件，配置好AndroidManifest.xml 

在AppActivity.java 的OnCreate初始化

```java
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mContext = this;
		Matrix.init(this);
		
	}
```
接着创建静态登录方法，直接调用360的登录方法
```java
	public static void  login360(){
		
		Intent intent = getLoginIntent();
		IDispatcherCallback callback = mLoginCallback;
		Matrix.execute(mContext, intent, callback);
		
	}
```

#### 5.jni调用登录方法 ####
在classes下创建SDK360.h 和SDK.cpp

SDK360.h 代码如下
```c++
     #ifndef __SDK360_H__
     #define __SDK360_H__

     #include "cocos2d.h"

     #include <string>  
     using namespace cocos2d;
     using namespace std;
     class SDK360
     {
      public:
      SDK360();
      virtual ~SDK360();
	  static SDK360*  getInstance();
	  void login();
     };


    #endif // __SDK360_H__
```
SDK360.cpp 代码如下
```c++
     #include "SDK360.h"
     #include <string>  
     #include <jni.h>
     #include "cocos2d.h"
     #include "platform/android/jni/JniHelper.h"

     using namespace std;
     static SDK360* mInstance=NULL;
     SDK360::SDK360()
     {
     }
     SDK360::~SDK360()
     {
     }
     SDK360* SDK360::getInstance()
     {
	if(NULL==mInstance)
	{
	mInstance=new SDK360();
	}
	return mInstance;
     }

    void SDK360::login()
    {

    #if(CC_TARGET_PLATFORM==CC_PLATFORM_ANDROID)
    JniMethodInfo minfo;
    bool isHave=JniHelper::getStaticMethodInfo(minfo,"org/cocos2dx/cpp/AppActivity","login360","()V");
	if(!isHave){
		
	}else{
	 minfo.env->CallStaticVoidMethod(minfo.classID,minfo.methodID);
	 minfo.env->DeleteLocalRef(minfo.classID);

	}
    #endif

    }

```

我们会在原工程右下角调用登录方法，需要修改HelloWorldScence.cpp

```c++
    #include "SDK360.h"
      ...

     void HelloWorld::menuCloseCallback(Ref* pSender)
     {
      SDK360::getInstance()->login();
     }


```

 最后在工程目录下jni-Android.mk 调用刚写好的SDK360.cpp

```c++
    LOCAL_SRC_FILES := hellocpp/main.cpp \
                   ../../Classes/AppDelegate.cpp \
                   ../../Classes/SDK360.cpp \
                   ../../Classes/HelloWorldScene.cpp
```

修改过c++文件都要重新编译一下.so文件
执行build_native.py 编译成功后你就可以run运行起来了。


ps: 调用360支付也同样道理.
