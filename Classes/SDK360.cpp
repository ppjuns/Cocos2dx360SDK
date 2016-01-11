
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
