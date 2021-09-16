//LocalStorage.js
//用法: https://github.com/sunnylqm/react-native-storage/blob/master/README.zh-CN.md
//原理：RocksDB/ SQLite
import Storage from 'react-native-storage';
import AsyncStorage from '@react-native-community/async-storage';

const storage = new Storage({
    // 最大容量，默认值1000条数据循环存储
    size: 1000,

    // 存储引擎：对于RN使用AsyncStorage，对于web使用window.localStorage
    // 如果不指定则数据只会保存在内存中，重启后即丢失
    storageBackend: AsyncStorage,

    // 此处不设置sync,直接进入catch  20210822
    autoSync: false,

    // 数据过期时间，默认一整天（1000 * 3600 * 24 毫秒），设为null则永不过期
    defaultExpires: null,

    // 读写时在内存中缓存数据。默认启用。
    enableCache: true,

    // 你可以在构造函数这里就写好sync的方法
    // 或是在任何时候，直接对storage.sync进行赋值修改
    // 或是写到另一个文件里，这里require引入
    // 如果storage中没有相应数据，或数据已过期，
    // 则会调用相应的sync方法，无缝返回最新数据。
    // sync方法的具体说明会在后文提到
});

export default storage;

//测试
// let now=new Date();
// storage.load({
//   key: 'loginState',
// })
// .then(ret => {
//   // 如果找到数据，则在then方法中返回
//   // 注意：这是异步返回的结果（不了解异步请自行搜索学习）
//   // 你只能在then这个方法内继续处理ret数据
//   // 而不能在then以外处理
//   // 也没有办法“变成”同步返回
//   // 你也可以使用“看似”同步的async/await语法
//   console.log(ret.nickname);
// })
// .catch(err => {
//   //如果没有找到数据且没有sync方法，
//   //或者有其他异常，则在catch中返回
//   console.warn(err.message);
// });
//
// let a=now.getMilliseconds();
// console.log('a=',a);
// storage.save({
//   key: 'loginState',
//   data: {
//     nickname: a,
//   }
// })
// .then(ret => {
//   console.log("save completed");
// });
//
// storage.load({
//   key: 'loginState',
// })
// .then(ret => {
//       console.log("then find:", ret.nickname);
//     }
// );
// }
