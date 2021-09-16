import React, {Component} from "react";
import {StyleSheet, Text, View} from "react-native";
import RNEChartsPro from "react-native-echarts-pro";
import {getFundEstimate} from "../../service/FundService";
import {Image} from 'react-native-elements';
import {Icon} from "react-native-elements";

export default class EstimateCharts extends Component {
  constructor(props) {
    super(props);
    if (props.code === undefined) {
      this.state = {
        code: '0',
        lineOption: {},
        received:true,
      }
    } else {
      console.log("x rendered");
      let x = [];
      //计算横轴时间,需要保持格式一致
      for (let i = 9; i < 15; i++) {
        let tmp = "";
        if (i === 9) {
          tmp += "0";
        }
        if (i === 12) {
          continue;
        }
        tmp = tmp + i.toString() + ":";
        for (let j = 0; j < 60; j++) {
          let tmp1 = tmp;
          if (i === 9 && j < 30) {
            continue;
          }
          if (i === 11 && j > 30) {
            continue;
          }
          if (j < 10) {
            tmp1 += "0";
          }
          tmp1 += j.toString();
          x.push(tmp1);
        }
      }
      x.push("15:00");
      this.state = {
        code: props.code,
        lineOption: {},
        xAxis: x,
        received:true,
      }
    }
    getFundEstimate(this.state.code, this.receiveData);
  }

  getWorkDate = () => {
    let yesterday = new Date();
    if (yesterday.getDay() === 0) {
      yesterday.setDate(yesterday.getDate() - 2);
    } else if (yesterday.getDay() === 1) {
      yesterday.setDate(yesterday.getDate() - 3);
    } else {
      yesterday.setDate(yesterday.getDate());
    }
    let year = yesterday.getFullYear().toString();
    let month = (yesterday.getMonth() + 1).toString();
    if (month.length < 2) {
      month = "0" + month;
    }
    let day = yesterday.getDate().toString();
    if (day.length < 2) {
      day = "0" + day;
    }
    let res = year + "-" + month + "-" + day;
    console.log(res);
    return res;
  }

  receiveData = (data) => {
    console.log("estimate charts data received");
    console.log(data);
    let xAxisData = this.state.xAxis;
    let yAxisData = [];
    if (data.Datas === undefined || data.Datas === null) {
      this.setState({
        received:false,
      })
      return;
    }
    let datas = data.Datas;
    let len = datas.length;
    let mx = -100.0000;
    let mn = 100.0000;
    for (let i = 0; i < len; i++) {
      let tmp = datas[i];
      let m = tmp.split(",");
      let now = parseFloat(m[2]).toFixed(4);
      yAxisData.push(now);
      mx = Math.max(mx, now);
      mn = Math.min(mn, now);
    }
    for (let i = len; i < xAxisData.length; i++) {
      yAxisData.push(null);
    }
    this.setState({
      code: this.state.code,
      xAxis: this.state.xAxis,
      received:true,
      lineOption: {
        legend: {
          orient: 'horizontal',
          x: 'left',
          y: 'top',
          data: ['估算涨幅'],
          left: 'center',
        },
        grid: {
          top: '16%',   // 等价于 y: '16%'
          left: '3%',
          bottom: '0%',
          containLabel: true
        },
        tooltip: {
          show: true,
          trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
          axisPointer: {  //坐标轴指示器，坐标轴触发有效，
            type: 'line', //默认为line，line直线，cross十字准星，shadow阴影
            crossStyle: {
              color: '#fff'
            }
          }
        },
        xAxis: {
          type: 'category',
          axisLabel: {
            showMinLabel: true,
            showMaxLabel: true,
            color: '#d1d1d1',
          },
          boundaryGap: false,
          data: xAxisData,
          axisLine: {
            onZero: false, //x轴不能在0上
            lineStyle: {
              color: '#f4f4f4',
              width: 1,
              //  shadowBlur:10,
            }
          },
        },
        yAxis: {
          type: 'value',
          //此处不设置最大值，最小值，否则会显示不出y轴 20210806
          // min: (mn-(mx-mn)*0.15).toFixed(2), // 设置y轴刻度的最小值
          // max: (mx+(mx-mn)*0.15).toFixed(2),  // 设置y轴刻度的最大值
          // splitNumber: 5,  // 设置y轴刻度间隔个数
          axisLine: {
            lineStyle: {
              // 设置y轴颜色
              color: 'rgba(0,0,0,0.78)'
            }
          },
          axisLabel: {
            formatter: '{value}%',
            color: '#d3d3d3',
          },
          splitLine: {
            lineStyle: {
              type: 'dotted',
            }
          }
        },
        animationDuration: 500, //渲染时长调整
        series: [
          {
            name: '净值估算',
            type: 'line',
            symbol: 'none',
            //    smooth:true,  //此处不加平滑，效果不太理想
            data: yAxisData,
            itemStyle: {
              normal: {
                lineStyle: {
                  width: 1,
                }
              }
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                  offset: 0, color: '#e7f0fd' // 0% 处的颜色
                }, {
                  offset: 1, color: '#fdfdfd' // 100% 处的颜色
                }],
                global: false // 缺省为 false
              },
              origin: 'start',
            },
            tooltip: {
              show: true,
              trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
              axisPointer: {  //坐标轴指示器，坐标轴触发有效，
                type: 'cross', //默认为line，line直线，cross十字准星，shadow阴影
                crossStyle: {
                  color: '#fff'
                },
              },
              //TODO:此处定义formatter不生效，需要另外寻找办法，因为当前显示的值没有百分号，交互不太理想 20210805
            },
          },
        ],
        color: ['#3d76d1'],
      }
    },()=>{
      this.forceUpdate();
    })
  }

  render() {
    if(this.state.received) {
      console.log("Estimate Charts rendered");
      return (
          <View>
            <RNEChartsPro height={250} option={this.state.lineOption}/>
            <View
                style={{flexDirection: 'row', margin: 'auto',paddingTop:14}}>
              <Icon
                  name='ios-information-circle-outline'
                  type='ionicon'
                  size={15}
                  color={'#5383ba'}
                  containerStyle={{}}
              />
              <Text
                  style={{color: '#9c9c9c', fontSize: 11, textAlign: 'center',paddingLeft:2}}>
                净值估算数据仅供参考，实际以基金公司披露净值为准。
              </Text>
            </View>
            <View
                style={{flexDirection: 'row', margin: 'auto', paddingTop: 5,paddingLeft:17}}>
              <Text
                  style={{color: '#9c9c9c', fontSize: 11, textAlign: 'center'}}>
                净值估算日期：{this.getWorkDate()}
              </Text>
            </View>
          </View>
      );
    }
    else {
      return (
          <View style={{height:300,alignItems:'center'}}>
            <Image
              source={require("../../../resources/image/no_data.png")}
              style={{
                height:150,
                width:150,
              }}
              containerStyle={{
                marginTop:50
              }}
              />
            <Text style={{marginTop:15,color:'#b0b0b0'}}>
              暂无数据
            </Text>
          </View>
      );
    }
  }
}
