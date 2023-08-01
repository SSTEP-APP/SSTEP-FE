package com.example.sstep.store.store_api;

import com.example.sstep.user.member.MemberResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StoreApiService {

    @POST("/store/register")
    Call<StoreResponseDto> saveStore(@Body StoreRequestDto storeRequestDto);


}



/*~~ 참고

@RequiredArgsConstructor
@RestController
@RequestMapping("/store")
public class StoreController {
    private final StoreService storeService;

    //사업장 등록
    @PostMapping("/register")
    public ResponseEntity<Void> registerStore(@RequestBody StoreRequestDto storeRequestDto) {
        storeService.saveStore(storeRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //직원 목록 조회
    @GetMapping("/{storeId}/staffs")
    public List<Staff> getStaffsByStoreId(@PathVariable Long storeId) {
        return storeService.getStaffsByStoreId(storeId);
    }

    //직원 추가 => 초대 방식
   @PostMapping("/{code}/add/staff")
    public void addStaffToStore(@PathVariable Long code, @RequestBody StaffRequestDto staffRequestDto) {
        storeService.addStaffToStore(code, staffRequestDto);
    }
}

*/