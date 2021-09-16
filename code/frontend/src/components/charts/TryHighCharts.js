import React from 'react';
import { StyleSheet, View } from 'react-native';
import HighchartsReactNative from '@highcharts/highcharts-react-native'


const modules = [
  'highcharts-more',
  'solid-gauge'
];

export default class TryHighCharts extends React.Component {
  constructor(props) {
    super(props);
      this.state = {
        chartOptions: {
          chart: {
            type: 'solidgauge'
          },
          series: [{
            data: [1]
          }]
        }
      };
    }

    render() {
      return (
          <View style={styles.container}>
            <HighchartsReactNative
                styles={styles.container}
                options={this.state.chartOptions}
                modules={modules}
            />
          </View>
      );
    }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: '#fff',
    justifyContent: 'center',
    flex: 1,
    height:400,
  }
});
