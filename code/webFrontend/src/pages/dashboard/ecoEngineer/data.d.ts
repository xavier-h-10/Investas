import { DataItem } from '@antv/g2plot/esm/interface/config';
import { queryWorkPlaceData } from '@/pages/dashboard/workplace/service';

export { DataItem };

export interface DescriptionType {
  descriptionId?: number;
  name?: string;
  text?: string;
  priority?: number;
  ruleList?: RuleType[];
}

export interface RuleType {
  ruleId?: number;
  descriptionId?: string;
  ruleType?: string;
  ruleOrientation?: string;
  ruleOrientationToShow?: string;
  ruleTypeToShow?: string;
  ruleValue?: number;
}

export interface ShowRuleSetUsingType {
  id?: number;
  name?: string;
  num?: number;
}

export interface WorkPlaceDataType {
  modelTypeData: ModelType[];
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

export interface RadarData {
  name: string;
  label: string;
  value: number;
}

export type AnalysisData = {
  visitData: VisitDataType[];
  visitData2: VisitDataType[];
  salesData: VisitDataType[];
  searchData: SearchDataType[];
  offlineData: OfflineDataType[];
  offlineChartData: OfflineChartData[];
  salesTypeData: VisitDataType[];
  salesTypeDataOnline: VisitDataType[];
  salesTypeDataOffline: VisitDataType[];
  radarData: DataItem[];
};

export type GeographicType = {
  province: {
    label: string;
    key: string;
  };
  city: {
    label: string;
    key: string;
  };
};

export type NoticeType = {
  id: string;
  title: string;
  logo: string;
  description: string;
  updatedAt: string;
  member: string;
  href: string;
  memberLink: string;
};

export type CurrentUser = {
  name: string;
  avatar: string;
  userid: string;
  notice: NoticeType[];
  email: string;
  signature: string;
  title: string;
  group: string;
  tags: TagType[];
  notifyCount: number;
  unreadCount: number;
  country: string;
  geographic: GeographicType;
  address: string;
  phone: string;
};

export type Member = {
  avatar: string;
  name: string;
  id: string;
};

export type ActivitiesType = {
  id: string;
  updatedAt: string;
  user: {
    name: string;
    avatar: string;
  };
  group: {
    name: string;
    link: string;
  };
  project: {
    name: string;
    link: string;
  };

  template: string;
};

export type RadarDataType = {
  label: string;
  name: string;
  value: number;
};
