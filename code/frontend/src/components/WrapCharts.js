import React from 'react'
import {StyleSheet, View} from 'react-native'
import {Text, Caption} from 'react-native-paper'
import TabCard from "./TabCard";
import message from "react-native-message/index";
import PerformanceCard from "./PerformanceCard";
import WrapLineECharts from "./charts/WrapLineECharts";
import EstimateCharts from "./charts/EstimateCharts";


export default function WrapCharts(props) {
  if (props.code == undefined || props.type == undefined) return null;
  let content = null;
  console.log("wrapcharts",props);
  if (props.type != 6) {
    content = [
      {
        header: '业绩走势',
        body: <WrapLineECharts code={props.code} type={props.type} id={3}/>
      },
      {
        header: '净值估算',
        body: <EstimateCharts code={props.code}/>
      },
    ]
  } else {
    content = [
      {
        header: '七日年化',
        body: <WrapLineECharts code={props.code} type={6} id={1}/>
      },
      {
        header: '万份收益',
        body: <WrapLineECharts code={props.code} type={6} id={2}/>
      },
      // {
      //   header: '净值估算',
      //   body: <EstimateCharts code={props.code}/>
      // },
    ]
  }
  return (
      <View>
        <TabCard
            content={content}
            onChange={(index) => {  }}
        />
      </View>
  )
}

const styles = StyleSheet.create({
  caption: {
    fontSize: 14,
  },
})
