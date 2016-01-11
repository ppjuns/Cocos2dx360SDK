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
