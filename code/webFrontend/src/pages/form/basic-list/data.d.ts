export type Member = {
  avatar: string;
  name: string;
  id: string;
};

export type BasicListItemDataType = {
  competitionId: number;
  competitionName: string;
  creatorId: string;
  creatorNickName: string;
  startDate: string;
  endDate: string;
  status: '已结束' | '进行中';
  logo: string;
  href: string;
  capacity: number;
  initialCapital: number;
  allowedFundType: string [];
  competitionDescription: string;
  isPublic: number;
  members: Member[];
};
