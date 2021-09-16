package com.fundgroup.backend.serviceImpl;

import com.fundgroup.backend.dto.FundSearch;
import com.fundgroup.backend.service.SearchService;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SearchServiceImpl implements SearchService {

	@Data
	private class Sub {
		private int hash;
		private String sub;
		private FundSearch fundSearch;

		public Sub(FundSearch fundSearch, String sub) {
			this.fundSearch = fundSearch;
			this.sub = sub;
			this.hash = sub.hashCode();
		}
	}

	private List<Sub> subs = new ArrayList<>();

	@Override
	public void load(List<FundSearch> fundSearches) {
		subs.clear();
		System.out.println("SearchService loading...");
		System.out.println(fundSearches.size());
		Hashtable<String, Boolean> hashtable = new Hashtable<>();
		for (FundSearch fundSearch : fundSearches) {
			String s = fundSearch.getCode();
			hashtable.clear();
			for (int i = 0; i < s.length(); i++)
				for (int j = i + 1; j <= s.length(); j++) {
					String t = s.substring(i, j);
					if (hashtable.containsKey(t)) continue;
					hashtable.put(t, true);
					subs.add(new Sub(fundSearch, t));
				}
			s = fundSearch.getName();
			for (int i = 0; i < s.length(); i++)
				for (int j = i + 1; j <= s.length(); j++) {
					String t = s.substring(i, j);
					if (hashtable.containsKey(t)) continue;
					hashtable.put(t, true);
					subs.add(new Sub(fundSearch, t));
				}
		}
		subs.sort((s1, s2) -> {
			if (s1 == null && s2 == null) return 0;
			if (s1 == null) return -1;
			if (s2 == null) return 1;
			if (s1.hash == s2.hash) {
				return s1.fundSearch.getCode().compareTo(s2.fundSearch.getCode());
			}
			if (s1.hash > s2.hash) return 1;
			return -1;
		});
		System.out.println(subs.size());
	}

	@Override
	public List<FundSearch> search(String search, Integer page, Integer size) {
		if (page * size > subs.size()) {
			return new ArrayList<>();
		}
		int hash = search.hashCode();
		int left = 0, right = subs.size() - 1, mid;
		while (left <= right) {
			mid = (left + right) / 2;
			if (subs.get(mid).getHash() == hash) {
				List<FundSearch> ret = new ArrayList<>();
				for (int i = mid; true; i--) {
					if (i < 0 || subs.get(i).getHash() != hash) {
						for (int j = i + 1; j < subs.size(); j++) {
							if (!subs.get(j).getSub().equals(search)) continue;
							if (subs.get(j).getHash() != hash) break;
							ret.add(subs.get(j).getFundSearch());
						}
						break;
					}
				}
				if (page * size > ret.size()) {
					return new ArrayList<>();
				}
				return ret.subList(page * size, Math.min((page + 1) * size, ret.size()));
			}
			if (subs.get(mid).getHash() < hash) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return new ArrayList<>();
	}
}
