function postRequest(url, json, callback) {
  console.log("post", url)
  let opts = {
    method: "POST",
    body: JSON.stringify(json),
    headers: {
      'Content-Type': 'application/json'
    },
    //credentials 是Request接口的只读属性，用于表示用户代理是否应该在跨域请求的情况下从其他域发送cookies。
    credentials: "include" //to upload cookies from client
  };

  fetch(url, opts)
  .then(response => response.json())
  .then(data => {
    callback(data);
  })
  .catch(error => {
    console.log(error);
  })
}

function postFile(url, fileUrl, callback) {
  // file是字段名，根据后端接受参数的名字来定,android上通过react-native-file-selector获取的path是不包含'file://'协议的，android上需要拼接协议为'file://'+path，而IOS则不需要,type可以是文件的MIME类型或者'multipart/form-data'
  // formData.append('file',{uri:'file://'+path,type:'multipart/form-data'})
  // 可能还会有其他参数 formData.append(key,value)
  let formData = new FormData();
  formData.append("image", {
    uri: fileUrl,
    name: 'image.jpg',
    type: 'image/jpg',
  });

  console.log("formdata:");
  console.log(JSON.stringify(formData));

  let opts = {
    method: "POST",
    body: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    //credentials 是Request接口的只读属性，用于表示用户代理是否应该在跨域请求的情况下从其他域发送cookies。
    credentials: "include" //to upload cookies from client
  };

  fetch(url, opts)
  .then(response => response.json())
  .then(data => {
    callback(data);
  })
  .catch(error => {
    console.log(error);
  })
}

const getRequest = (url, callback) => {
  console.log("get", url)

  let opts = {
    method: "GET",
    credentials: "include" //to upload cookies from client
  };

  fetch(url, opts)
  .then(response => response.json())
  .then(data => {
    callback(data);
  })
  .catch(error => {
    console.log(error);
  })
}

export {postRequest, getRequest, postFile}
