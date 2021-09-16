import DeviceInfo from 'react-native-device-info';


//获取设备标识码 文档：https://github.com/react-native-device-info/react-native-device-info#getuniqueid
//根据文档所述，iOS会获取IDFV(identifierForVendor)，这个标识码是持久化的；Android会获取Android_ID,手机启动后保持不变，手机重启后经试验也保持不变
export default function GetDeviceInfo() {
    let uniqueId=DeviceInfo.getUniqueId();
    console.log("uniqueId=",uniqueId);
    return uniqueId;
}
