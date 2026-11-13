package com.example.testit;

import com.example.testit.adapter.mail.MailService;
import com.example.testit.model.Book;
import com.example.testit.model.Member;
import com.example.testit.model.Status;
import com.example.testit.repository.BookRepository;
import com.example.testit.repository.MemberRepository;
import com.example.testit.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.assertj.core.api.Assertions;

import java.util.Optional;

public class BookServiceTest {
    
    BookService bookService;
    BookRepository bookRepository;
    MemberRepository memberRepository;
    MailService mailService;

    @BeforeEach
    void setUp(){
        bookRepository = Mockito.mock(BookRepository.class);
        memberRepository = Mockito.mock(MemberRepository.class);
        mailService = Mockito.mock(MailService.class);

        bookService = new BookService(bookRepository, memberRepository, mailService);
    }

    @Test
    public void test1(){
        //given
        Book book = new Book();
        Member member = new Member();
        book.setId(1L);
        member.setId(1L);
        
        //when
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Mockito.when(bookRepository.save(book)).thenReturn(book);
        
        Book emprunt = bookService.borrowBook(book.getId(),member.getId());
        
        //then
        Assertions.assertThat(emprunt.getStatus()).isEqualTo(Status.EMPRUNTE);
        Assertions.assertThat(emprunt.getBorrower().getId()).isEqualTo(member.getId());


        
    }
        

}

