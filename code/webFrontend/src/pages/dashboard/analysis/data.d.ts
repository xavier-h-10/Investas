import { DataItem } from '@antv/g2plot/esm/interface/config';

export { DataItem };

export interface monitorDataType {
  latestPredictionData?: DataItem[];
  NAVData?: DataItem[];
  historyData?: DataItem[];
  basicFundTypeData?: DataItem[];
  fundCompetitionData?: DataItem[];
  visitData2?: DataItem[];
  salesData?: DataItem[];
  searchData?: DataItem[];
}

export interface VisitDataType {
  x: string;
  y: number;
}

export interface PredictionErrorType {
  errorId: number;
  fundCode: string;
  fundType: number;
  lastUpdateTimestamp: string;
  todayMSE: number;
  todayRMSE: number;
  todayMAE: number;
  todayAbsDelta: number;
}

export interface ShowDateNumType {
  localDate: string;
  num: number;
}

export type SearchDataType = {
  index: number;
  keyword: string;
  count: number;
  range: number;
  status: number;
};

export type OfflineDataType = {
  name: string;
  cvr: number;
};

export interface OfflineChartData {
  date: number;
  type: number;
  value: number;
}

export type RadarData = {
  name: string;
  label: string;
  value: number;
};

export interface AnalysisData {
  visitData: DataItem[];
  visitData2: DataItem[];
  salesData: DataItem[];
  searchData: DataItem[];
  offlineData: OfflineDataType[];
  offlineChartData: DataItem[];
  salesTypeData: DataItem[];
  salesTypeDataOnline: DataItem[];
  salesTypeDataOffline: DataItem[];
  radarData: RadarData[];
}
