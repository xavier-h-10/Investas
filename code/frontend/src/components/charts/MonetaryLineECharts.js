import React, {Component} from "react";
import {StyleSheet, Text, View} from "react-native";
import RNEChartsPro from "react-native-echarts-pro";
import {ScrollView} from "react-native";
import {Card} from "react-native-elements";
import {getFundNAV} from "../../service/FundService";

//用法 y轴刻度要单独算，根据近1月，近3月来决定范围
export default class MonetaryLineECharts extends Component {
  constructor(props) {
    super(props);
    let timeRange = [2, 3, 4, 5, 7];
    this.state = {
      code: props.code,
      index:props.index,
      type: timeRange[props.index],
      lineOption:{},
    }
    getFundNAV(this.state.code, this.state.type, this.receiveData);
  }

  receiveData = (data) => {
    let xAxisData = [];
    let yAxisData = [];
    console.log("monetary line");
    console.log(data.data);
    if (data.data == undefined || data.data.dailylist == undefined
        || data.data.dailylist.length == 0 || data.data.stockdailylist.length
        == 0) {
      return;
    }
    let len = data.data.dailylist.length;
    let mx = 0.00;
    let mn = 0.00;
    console.log("hello",data.data.dailylist[0]);
    if (this.props.id == undefined || this.props.id == 1) {
      for (let i = 0; i < len; i++) {
        let tmp = data.data.dailylist[i];
        let tmp1 = ((tmp.accumulateNAV + 0)).toFixed(4);
        xAxisData.push(tmp.updateDate);
        yAxisData.push(tmp1);
        mx = Math.max(mx, tmp1);
        mn = Math.min(mn, tmp1);
      }
    } else {
      for (let i = 0; i < len; i++) {
        let tmp = data.data.dailylist[i];
        let tmp1 = ((tmp.nav + 0)).toFixed(4);
        xAxisData.push(tmp.updateDate);
        yAxisData.push(tmp1);
        mx = Math.max(mx, tmp1);
        mx = Math.max(mx, tmp1);
        mn = Math.min(mn, tmp1);
      }
    }

    let min_range = mn;
    let max_range = mx;

    function formatter(value) {
      return value.toFixed(4)+"%";
    }

    this.setState({
      code: this.state.code,
      index: this.state.index,
      type: this.state.type,
      lineOption: {
        label: {
          rich: {
            black: {
              color: '#070707',
            },
            green: {
              color: '#46a08b',
            },
            red: {
              color: '#e45e2e',
            }
          },
        },
        legend: {
          // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
          orient: 'horizontal',
          // x 设置水平安放位置，默认全图居中，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
          x: 'left',
          // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
          y: 'top',
          data: ['本基金'],
          left: 'center',
        },
        //  图表距边框的距离,可选值：'百分比'¦ {number}（单位px）
        grid: {
          top: '16%',   // 等价于 y: '16%'
          left: '3%',
          bottom: '0%',
          containLabel: true
        },

        // 提示框
        tooltip: {
          show: true,
          trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
          axisPointer: {  //坐标轴指示器，坐标轴触发有效，
            // type: 'cross', //默认为line，line直线，cross十字准星，shadow阴影
            type:'line',
            snap:'true',   //设置自动吸附
            crossStyle: {
              color: '#3364ff',
            }
          },
          // formatter:'{c0}%',
          // formatter:'{b}<br/>本基金:{c0}%<br/>沪深300:{c1}%',
        },
        xAxis: {
          type: 'category',
          splitNumber:2,
          axisLabel: {
            showMinLabel: true,
            showMaxLabel: true,
            color: '#d1d1d1',
            formatter: function (value, index) {
              if (value.length < 10) {
                return value;
              } else {
                return value.substring(5);
              }
            }
          },
          // boundaryGap值为false的时候，折线第一个点在y轴上
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
          //此处进行修改 保留小数效果不好
          min: Math.max(0,Math.round((min_range-(max_range-min_range)*0.15))), // 设置y轴刻度的最小值
          max: Math.round((max_range+(max_range-min_range)*0.15)),  // 设置y轴刻度的最大值
          splitNumber: 5,  // 设置y轴刻度间隔个数
          axisLine: {
            lineStyle: {
              // 设置y轴颜色
              color: 'rgba(0,0,0,0.78)'
            }
          },
          axisLabel: {
            // formatter: '{value}%',
            formatter: function (value) {
              // return formatter(value);
              return value.toFixed(4);
            },
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
            name: '本基金',
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
                type: 'line', //默认为line，line直线，cross十字准星，shadow阴影
                crossStyle: {
                  color: '#fff'
                },
              },
              // formatter: "{a}><br/>{b}:{c}({d}%)",
              // formatter:"{a}><br/>{b}:{c}({d}%)",
              // formatter:function(params) {
              //   return params.value[params.encode.x[0]];
              // }
            }
            ,
          },
        ],
        color: ['#3d76d1', '#2bb8d6', '#daae45'],
        // color: ['#3d76d1'],
      },
      data: data,
    });
  }

  componentWillReceiveProps(nextProps, nextState) {
    let timeRange = [2, 3, 4, 5, 7];
    if ((nextProps.index == undefined || nextProps.index == this.state.index) && nextProps.id == this.props.id) {
      return;
    }
    this.setState({
      code: nextProps.code,
      index: nextProps.index,
      type: timeRange[nextProps.index],
      lineOption: this.state.lineOption,
    })
    getFundNAV(nextProps.code, timeRange[nextProps.index], this.receiveData);
  }

  render() {
    // console.log("LineECharts rendered type=",this.state.type,"id=",this.props.id);
    return (
        // <Card containerStyle={{height:300,paddingTop:25,borderRadius: 10}}>  //不能使用card,否则tooltip不能滑动
        // <View style={{alignItems:'center',width:'80%',minHeight:350}}>
        <RNEChartsPro
            height={250}
            option={this.state.lineOption}
            onPress={res => alert(JSON.stringify(res))}
        />
    );
  }
}
