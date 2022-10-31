package spring;

import org.apache.catalina.mapper.Mapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDao {

	private JdbcTemplate jdbcTemplate;

	public MemberDao(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?", // query() 이용해서 쿼리 실행
				new RowMapper<Member>() {
					@Override
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException { // ResultSet에서 데이터 읽어와 Member객체 생성후 리턴
						Member member = new Member(
								rs.getString("EMAIL"),
								rs.getString("PASSWORD"),
								rs.getString("NAME"),
								rs.getTimestamp("REGDATE").toLocalDateTime());
						member.setId(rs.getLong("ID"));
						return member;
					}
				}, email); // email이 인덱스 파라미터(?)에 들어갈 값.

		return results.isEmpty() ? null : results.get(0);
	}

	public List<Member> selectAll(){
		List<Member> results = jdbcTemplate.query("select * from MEMBER",
								new RowMapper<Member>() {
					@Override
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
						Member member = new Member(
								rs.getString("EMAIL"),
								rs.getString("PASSWORD"),
								rs.getString("NAME"),
								rs.getTimestamp("REGDATE").toLocalDateTime());
						member.setId(rs.getLong("ID"));
						return member;
					}
				});
		return results;
	}

	public int count(){
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from MEMBER", Integer.class);
		return count;
	}

	public void update(Member member){
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				// 파라미터로 전달받은 Connection을 이용해서 PreparedStatment 생성
				PreparedStatement pstmt = con.prepareStatement(
						"insert IGNORE into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) values (?,?,?,?)");
				// 인덱스 파라미터의 값 설정
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));

				// 생성한 PreparedStatement 객체 리턴
				return pstmt;

			}
		});

	}

	public void insert(final Member member){
		// GeneratedKeyHolder 객체 생성 ( 자동 생성된 키값 구해주는 KeyHolder 구현 클래스)
		KeyHolder keyHolder = new GeneratedKeyHolder();
		// 파라미터1 : PreparedStatementCreater, 파라미터2 : KeyHolder
		jdbcTemplate.update(new PreparedStatementCreator() {
			/*
			1. PreparedStatementCreator 임의 클래스 이용해서 PreparedStatement 객체 직접 생성.
			2. Connection의  preparedStatement() 를 이용해서 PreparedStatement 객체 생성 시,
			두번째 파라미터로 String 배열일 {"ID"}를 줌. 자동생성하는 키칼럼 지정
			 */
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						"insert IGNORE into MEMBER(EMAIL, PASSWORD, NAME, REGDATE) " +
								"values(?,?,?,?)",
						new String[]{"ID"});
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4,
						Timestamp.valueOf(member.getRegisterDateTime()));
				return pstmt;
			}
			// jdbcTemplate.update() 의 두 번째 파라미터로 KeyHolder 객체 전달.
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}
//	public void update(Member member){
//		jdbcTemplate.update(
//				"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
//				member.getName(), member.getPassword(), member.getEmail());
//	}
}

