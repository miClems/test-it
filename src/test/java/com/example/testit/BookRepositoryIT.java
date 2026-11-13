package com.example.testit;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;

import jakarta.transaction.Transactional;

@SpringBootTest 
public class BookRepositoryIT {
    
    @Autowired 
    BookRepository bookRepository;

    @Autowired 
    MemberRepository memberRepository;

    @Test
    public void test2(){
        //given
        Member member1 = memberRepository.save(new Member());
        Member member2 = memberRepository.save(new Member());

        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        Book book4 = new Book();

        book1.setStatus(Status.EMPRUNTE);
        book2.setStatus(Status.EMPRUNTE);
        book3.setStatus(Status.EMPRUNTE);
        book4.setStatus(Status.EMPRUNTE);

        book1.setBorrower(member1);
        book2.setBorrower(member1);
        book3.setBorrower(member2);
        book4.setBorrower(member2);
        
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        //when
        List<Book> result1 = bookRepository.findByMemberAndStatus(member1.getId(), Status.EMPRUNTE);
        List<Book> result2 = bookRepository.findByMemberAndStatus(member2.getId(), Status.EMPRUNTE);
        
        //then
        Assertions.assertThat(result1).extracting(Book::getId)
            .containsExactlyInAnyOrder(book1.getId(), book2.getId());
        Assertions.assertThat(result2).extracting(Book::getId)
            .containsExactlyInAnyOrder(book3.getId(), book4.getId());

    }
}
