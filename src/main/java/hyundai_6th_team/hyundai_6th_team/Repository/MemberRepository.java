package hyundai_6th_team.hyundai_6th_team.Repository;

import hyundai_6th_team.hyundai_6th_team.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
