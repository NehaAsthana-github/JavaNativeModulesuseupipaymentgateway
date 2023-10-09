import { View, Text,NativeModules, Pressable, ToastAndroid } from 'react-native'
import React from 'react'

const App = () => {
  const { MyToastNativeModule}=NativeModules

  const showhandler=()=>{
    MyToastNativeModule.subModuleMethode()
  }

  const showalert=()=>{
    MyToastNativeModule.showAlertMessage("neha",(err,message)=>{

      alert(`message:${message}`)
    })
  }
  const upihandler=()=>{
    MyToastNativeModule.initiateUpiPayment("bhartidaughteroframe.39372041@hdfcbank","1.00",(err,message)=>{
      if(err){
        ToastAndroid.show(err,ToastAndroid.LONG)
      }
      else{
        ToastAndroid.show(message,ToastAndroid.LONG)
      }
    })
  }
  return (
    <View>
      <Pressable onPress={()=>showhandler()}>
      <Text>App</Text>
      </Pressable>
       <Pressable onPress={()=>showalert()}>
      <Text>alert show</Text>
      </Pressable>
      <View style={{alignItems:'center',justifyContent:'center',marginTop:90,borderWidth:2,
      width:100,alignSelf:'center'}}>
      <Pressable onPress={()=>upihandler()}>
      <Text>upi btn</Text>
      </Pressable>
      </View>
    </View>
  )
}

export default App

