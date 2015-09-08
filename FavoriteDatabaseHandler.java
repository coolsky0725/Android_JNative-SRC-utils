package com.fidku.jeloubeta.utils;
	public class FavoriteDatabaseHandler {

		private long id;
		public String site_index;
		
		public String site_indexstr;
		public String site_url;
		public String favor_id;
	    public long getId() {
			return id;
		}
		
		public String getIdInString() {
			return Long.toString(id);
		}
		
		public String getName() {
			return site_index;
		}
		public String getNamestr() {
			return site_indexstr;
		}

		public String getUrl() {
			return site_url;
		}
		public String getfavId() {
			return favor_id;
		}
		
		public void setName(String name) {
			this.site_index = name;
		}
		public void setNamestr(String namestr) {
			this.site_indexstr = namestr;
		}
		
		public void setUrl(String url) {
			this.site_url = url;
		}
		
		public void setfavId(String favid) {
			this.favor_id = favid;
		}
		
	    public void setId(long id) {
			this.id = id;
		}
}
